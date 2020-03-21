import XQUERYgen.XQUERYLexer;
import XQUERYgen.XQUERYParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.*;

/**
 * XQuery Optimizer
 * Input an XQuery comfort format as following:
 * Format :
 *      XQuery ::= ’for’ V1 ’in’ Path1’,’ ..., Vn ’in’ Pathn ’where’ Cond ’return’ Return
 *
 *      Path   ::= (’doc(’FileName’)’|Var) Sep n1 Sep ... Sep nm
 *             |   (’doc(’FileName’)’|Var) Sep n1 Sep ... Sep ’text()’
 *
 *      Sep    ::= ’/’
 *             |   ’//’
 *
 *      Return ::= Var
 *             | Return ’,’ Return
 *             | ’<’n’>’ Return ’</’n’>’
 *             | Path
 *
 *      Cond   ::= (Var|Constant) ’eq’ (Var|Constant)
 *             |   Cond ’and’ Cond
 *      Constant ::= StringLiteral
 * Then optimize the XQuery and return the reorganized XQuery which contains "join" key word.
 */
class VarTreeNode {
    String var;
    List<VarTreeNode> children;
    public VarTreeNode(String var) {
        this.var = var;
        this.children = new ArrayList<>();
    }
}

class JoinTreeNode {
    Set<String> joinedTables;     // tables to be joined
    List<String> joinConditions;  // join conditions of left and right subtree, left.attri = right.attri
    JoinTreeNode left;            // left and right join subtree
    JoinTreeNode right;
    int numOfCP;
    int height;
    public JoinTreeNode(Set<String> joinedTables, List<String> joinConditions, int height, int numOfCP) {
        this.joinedTables = joinedTables;
        this.joinConditions = joinConditions;
        this.height = height;
        this.numOfCP = numOfCP;
    }
}
public class XQueryOptimizer {
    public static InputStream optimize(InputStream in, String joinBias, String outputXQueryPath) throws IOException {
        // read in the XQuery text & get the xq context
        ANTLRInputStream input = new ANTLRInputStream(in);
        XQUERYLexer xqueryLexer = new XQUERYLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xqueryLexer);
        XQUERYParser xqueryParser = new XQUERYParser(commonTokenStream);
        XQUERYParser.XqContext xqContext = xqueryParser.xq();
        File outputFile = null;
        try {
            outputFile = new File(outputXQueryPath);
            if (outputFile.exists()) { outputFile.delete(); }
            File dir = new File(outputFile.getParent());
            dir.mkdirs();
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // detect join, if XQuery has join, then return directly
        if (xqContext.getChildCount() < 3 || xqContext.getText().toLowerCase().matches("join")) {
            return null;
        }

        // get the forClause, whereClause and returnClause context for later analysis
        XQUERYParser.ForClauseContext forClauseContext = (XQUERYParser.ForClauseContext) xqContext.getChild(0);
        XQUERYParser.WhereClauseContext whereClauseContext = (XQUERYParser.WhereClauseContext) xqContext.getChild(1);
        XQUERYParser.ReturnClauseContext returnClauseContext = (XQUERYParser.ReturnClauseContext) xqContext.getChild(2);

        /**
         * valueMap: record the var and its corresponding XQuery
         * forest: record the relationship of the variables
         * joinPart: record the join information after analysing whereClause
         *           key: table1&table2 ==> means table1 join with table2
         *                table2        ==> means table3 has a filter
         *           value: list of join key of form [a=b, c=d, ...]
         *                  list of filter key of form [a="StringConstant", ..]
         * tupleMap: record the variable to its corresponding tuple
         */
        Map<String, String> valueMap = new HashMap<>();
        Map<String, VarTreeNode> forest = new HashMap<>();
        Map<String, List<String>> joinPart = new HashMap<>();
        Map<String, String> tupleMap = new HashMap<>();
        Set<String> setOfTextNode = new HashSet<>();

        // analyse for clause
        analysisForClause(valueMap, forest, forClauseContext, setOfTextNode);

        // analysis where clause
        analysisWhereClause(forest, joinPart, whereClauseContext.cond());

        // rewrite for & where clause
        String forWhereClause = reformForWhere(valueMap, forest, joinPart, tupleMap, joinBias);

        // rewrite return clause
        String returnClause = reformReturn(returnClauseContext, tupleMap, setOfTextNode);

        // output reformed XQuery
        String reformedXQuery = forWhereClause + returnClause;

        // output the reformed XQuery to File
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(reformedXQuery);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(reformedXQuery.getBytes());
    }

    private static void analysisForClause(Map<String, String> valueMap, Map<String, VarTreeNode> forest, XQUERYParser.ForClauseContext forClauseContext, Set<String> listOfTextNode) {
        int varNum = forClauseContext.Var().size();
        for (int i = 0; i < varNum; i++) {
            String var = forClauseContext.Var(i).getText();
            String xq = forClauseContext.xq(i).getText();
            if (xq.toLowerCase().endsWith("text()") || xq.toLowerCase().endsWith("text(),")) { listOfTextNode.add(var); }
            if (xq.startsWith("$")) {
                int indexOfSlash = xq.indexOf("/");
                String parent =  indexOfSlash == -1 ? xq : xq.substring(0, indexOfSlash);
                for (String root : forest.keySet()) {
                    boolean result = insertVar(forest.get(root), var, parent);
                    if (result) { break; }
                }
            } else {
                forest.put(var, new VarTreeNode(var));
            }
            valueMap.put(var, xq);
        }
    }

    private static boolean insertVar(VarTreeNode root, String var, String parent) {
        if (root == null) { return false; }
        if (root.var.equals(parent)) {
            root.children.add(new VarTreeNode(var));
            return true;
        } else {
            for (VarTreeNode child : root.children) {
                if (insertVar(child, var, parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void analysisWhereClause(Map<String, VarTreeNode> forest, Map<String, List<String>> joinPart, XQUERYParser.CondContext condContext) {
        if (condContext.getChild(1).getText().equals("and")) {
            analysisWhereClause(forest, joinPart, (XQUERYParser.CondContext) condContext.getChild(0));
            analysisWhereClause(forest, joinPart, (XQUERYParser.CondContext) condContext.getChild(2));
        } else {
            String s1 = condContext.getChild(0).getText();
            String s2 = condContext.getChild(2).getText();
            String root1 = "";
            String root2 = "";

            for (String root : forest.keySet()) {
                if (findVar(forest.get(root), s1)) {
                    root1 = root;
                    break;
                }
            }

            if (s2.startsWith("$")) {
                for (String root : forest.keySet()) {
                    if (findVar(forest.get(root), s2)) {
                        root2 = root;
                    }
                }
            }

            String key = root2.equals("") ? root1 : (root1 + "&" + root2);
            String value = s1 + "=" + s2;
            if (!joinPart.containsKey(key)) { joinPart.put(key, new ArrayList<String>()); }
            joinPart.get(key).add(value);
        }
    }

    private static boolean findVar(VarTreeNode root, String var) {
        if (root == null) { return false; }
        if (root.var.equals(var)) {
            return true;
        } else {
            for (VarTreeNode node : root.children) {
                if (findVar(node, var)) { return true; }
            }
        }
        return false;
    }

    private static String joinTreeRewriter(String tuple, JoinTreeNode root, Map<String, String> valueMap,  Map<String, VarTreeNode> forest, Map<String, List<String>> joinPart, Map<String, String> tupleMap) {
        if (root.left == null && root.right == null) {
            StringBuilder result = new StringBuilder();
            String table = new ArrayList<>(root.joinedTables).get(0);
            List<String> varibleList = getVaribleList(forest.get(table));
            buildTupleVarMap(varibleList, tuple, tupleMap);
            String forPart = formInnerForClause(valueMap, varibleList);
            StringBuilder filterPartSB = new StringBuilder();
            if (joinPart.containsKey(table)) {
                filterPartSB.append(joinPart.get(table).get(0));
                for (int i = 1; i < joinPart.get(table).size(); i++) {
                    filterPartSB.append(" and " + joinPart.get(table).get(i));
                }
            }
            String filterPart = filterPartSB.toString();
            String returnPart = formInnerReturnClause(varibleList, tuple);
            result.append("for " + forPart + "\n")
                    .append(filterPart.length() == 0 ? "" : "where " + filterPart.toString() + "\n")
                    .append(returnPart + "\n");
            return result.toString();
        }
        StringBuilder res = new StringBuilder("join ( \n");
        String left = joinTreeRewriter(tuple, root.left, valueMap, forest, joinPart, tupleMap);
        String right = joinTreeRewriter(tuple, root.right, valueMap, forest, joinPart, tupleMap);
        String joinCond = root.joinConditions.size() == 0 ? "[],[]" : getJoinCond(root.joinConditions,false);
        res.append(left + ",\n")
                .append(right + ",\n")
                .append(joinCond + "\n")
                .append(")\n");
        return res.toString();
    }

    private static String reformForWhere(Map<String, String> valueMap, Map<String, VarTreeNode> forest, Map<String, List<String>> joinPart, Map<String, String> tupleMap, String joinBias) {
        // separate join group
//        List<List<String>> joinGroups = separateJoinGroup(joinPart);
        JoinTreeNode joinTree = generateJoinPlan(joinPart, forest, joinBias);
        StringBuilder result = new StringBuilder();
        String tuple = "tuple";
        String partResult = joinTreeRewriter(tuple, joinTree, valueMap, forest, joinPart, tupleMap);
        result.append("for " + "$" + tuple + " in " + partResult + "\n");
        return result.toString();
    }

    private static List<String> getVaribleList(VarTreeNode root) {
        List<String> result = new ArrayList<>();
        Queue<VarTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            VarTreeNode node = queue.poll();
            result.add(node.var);
            for (VarTreeNode child : node.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    private static void buildTupleVarMap(List<String> varibleList, String tuple, Map<String, String> tupleMap) {
        for (String var : varibleList) {
            tupleMap.put(var.substring(1), tuple);
        }
    }

    private static String formInnerForClause(Map<String, String> valueMap, List<String> variableList) {
        StringBuilder result = new StringBuilder();
        for (String variable : variableList) {
            result.append(variable + " in " + valueMap.get(variable) + ",\n");
        }
        return result.substring(0, result.length() - 2);
    }

    private static String formInnerReturnClause(List<String> variableList, String tuple) {
        StringBuilder result = new StringBuilder();
        result.append("return <" + tuple + ">{\n");
        for (String variable : variableList) {
            String tag = variable.substring(1);
            result.append("<" + tag + ">{" + variable + "}</" + tag + ">,\n");
        }
        result.delete(result.length() - 2, result.length() - 1);
        result.append("}</" + tuple + ">");
        return result.toString();
    }

    private static String getJoinCond(List<String> joinKW, boolean reverse) {
        StringBuilder part1 = new StringBuilder("[");
        StringBuilder part2 = new StringBuilder("[");
        for (String kw : joinKW) {
            String[] kwords = kw.split("=");
            part1.append(kwords[0].substring(1) + ",");
            part2.append(kwords[1].substring(1) + ",");
        }
        part1.setCharAt(part1.length() - 1, ']');
        part2.setCharAt(part2.length() - 1, ']');
        return reverse ? part2.toString() + "," + part1.toString() : part1.toString() + "," + part2.toString();
    }

    private static String reformReturn(XQUERYParser.ReturnClauseContext returnClauseContext, Map<String, String> tupleMap, Set<String> listOfTextNode) {
        Set<String> varToReplace = new HashSet<>();
        String returnClause = returnClauseContext.getText();
        findVarToReplace(returnClauseContext.getChild(1), varToReplace);
        for (String var : varToReplace) {
            if (tupleMap.get(var) == null) { continue; }
            if (listOfTextNode.contains("$" + var)) {
                returnClause = returnClause.replaceAll("\\$" + var,   "\\$" + tupleMap.get(var) + "/" + var + "/text()");
            } else {
                returnClause = returnClause.replaceAll("\\$" + var,   "\\$" + tupleMap.get(var) + "/" + var + "/*");
            }
        }
        return returnClause;
    }

    private static void findVarToReplace(ParseTree root, Set<String> varToReplace) {
        if (root == null) { return; }
        if ( root.getChildCount() == 0 && root.getText().startsWith("$")) { varToReplace.add(root.getText().trim().substring(1)); }
        for (int i = 0; i < root.getChildCount(); i++) {
            findVarToReplace(root.getChild(i), varToReplace);
        }
    }

    // milestone 4

    private static JoinTreeNode generateJoinPlan(Map<String, List<String>> joinInfo, Map<String, VarTreeNode> forest, String joinBias) {
        // generate join group
        List<String> joinGroup = new ArrayList<>(forest.keySet());
        JoinTreeNode joinTree = null;
        if (joinBias.equals("L") || joinBias.equals("-L") ) {
            joinTree =  generateLeftJoinTree(joinInfo, joinGroup);
        }
        if (joinBias.equals("B") || joinBias.equals("-B") ) {
            joinTree = generateBushyJoinTree(joinInfo, joinGroup);
        }
        return joinTree;
    }

    private static JoinTreeNode generateLeftJoinTree(Map<String, List<String>> joinInfo, List<String> joinGroup) {
        Set<String> table = new HashSet<>();
        table.add(joinGroup.get(0));
        JoinTreeNode left = new JoinTreeNode(table, new ArrayList<String>(), -1, -1);
        for (int i = 1; i < joinGroup.size(); i++) {
            Set<String> tableTemp = new HashSet<>();
            tableTemp.add(joinGroup.get(i));
            JoinTreeNode right = new JoinTreeNode(tableTemp, new ArrayList<String>(), -1, -1);
            List<String> joinCond = new ArrayList<>();
            isConnected(left, right, joinInfo, joinCond);
            left = createJoinTree(left, right, new HashSet<>(joinGroup.subList(0, i + 1)), joinCond, -1);
        }
        return left;
    }

    private static JoinTreeNode generateBushyJoinTree(Map<String, List<String>> joinInfo, List<String> joinGroup) {
        // using dynamic programming to generate bushy tree
        Map<Set<String>, JoinTreeNode> dp = new HashMap<>();
        // base case
        for (String table : joinGroup) {
            Set<String> joinTables = new HashSet<>();
            List<String> joinCond = new ArrayList<>();

            joinTables.add(table);
            if (joinInfo.containsKey(table)) {
                joinCond.addAll(joinInfo.get(table));
            }
            JoinTreeNode node = new JoinTreeNode(new HashSet<>(joinTables), joinCond, 1, 0);
            dp.put(joinTables, node);
        }

        for (int i = 2; i < (int) Math.pow(2, joinGroup.size()); i++) {
            List<String> S = new ArrayList<>();
            int j = 1;
            int temp = (int) Math.floor(i / Math.pow(2, j - 1));
            while (temp > 0 && j <= joinGroup.size()) {
                if (temp % 2 == 1) { S.add(joinGroup.get(j - 1)); }
                j++;
                temp = (int) Math.floor(i / Math.pow(2, j - 1));
            }
            if (S.isEmpty()) { continue; }
            List<Set<String>> subsets = new ArrayList<>();
            getSubsets(S, subsets, new ArrayList<String>(), 0);
            for (Set<String> set1 : subsets) {
                Set<String> set2 = getRest(set1, S);
                if (!dp.containsKey(set1) || !dp.containsKey(set2)) { continue; }
                JoinTreeNode left = dp.get(set1);
                JoinTreeNode right = dp.get(set2);
                List<String> joinCond = new ArrayList<>();
                boolean isConnecte = isConnected(left, right, joinInfo, joinCond);
                // if (!isConnecte) { continue; }
                Set<String> S_set = new HashSet<>(S);
                int numOfCP = left.numOfCP + right.numOfCP;
                if (!isConnecte) { numOfCP++; }
                JoinTreeNode root = createJoinTree(left, right, S_set, joinCond, numOfCP);
                if (!dp.containsKey(S_set)) {
                    dp.put(S_set, root);
                } else if (root.numOfCP < dp.get(S_set).numOfCP) {
                    dp.put(S_set, root);
                } else if (root.height < dp.get(S_set).height) {
                    dp.put(S_set, root);
                }
            }
        }
        return dp.get(new HashSet<>(joinGroup));
    }

    private static void getSubsets(List<String> S, List<Set<String>> subsets, List<String> list, int pos) {
        if (!list.isEmpty()) { subsets.add(new HashSet<>(list)); }
        for (int i = pos; i < S.size(); i++) {
            list.add(S.get(i));
            getSubsets(S, subsets, list, i + 1);
            list.remove(list.size() - 1);
        }
    }

    private static Set<String> getRest(Set<String> set1, List<String> S) {
        Set<String> result = new HashSet<>();
        for (String s : S) {
            if (!set1.contains(s)) { result.add(s); }
        }
        return result;
    }

    private static JoinTreeNode createJoinTree(JoinTreeNode left, JoinTreeNode right, Set<String> S_set, List<String> joinCond, int numOfCP) {
        JoinTreeNode root = new JoinTreeNode(S_set, joinCond, Math.max(left.height, right.height) + 1, numOfCP);
        root.left = left;
        root.right = right;
        return root;
    }

    private static boolean isConnected(JoinTreeNode left, JoinTreeNode right, Map<String, List<String>> joinInfo, List<String> joinCond) {
        boolean isConnected = false;
        for (String leftTable : left.joinedTables) {
            for (String rightTable : right.joinedTables) {
                String key1 = leftTable + "&" + rightTable;
                String key2 = rightTable + "&" + leftTable;
                if (joinInfo.containsKey(key1)) {
                    isConnected = true;
                    joinCond.addAll(joinInfo.get(key1));
                }
                if (joinInfo.containsKey(key2)) {
                    isConnected = true;
                    List<String> tempConds = joinInfo.get(key2);
                    for (String tempCond : tempConds) {
                        String[] attri = tempCond.split("=");
                        joinCond.add(attri[1] + "=" + attri[0]);
                    }
                }
            }
        }
        return isConnected;
    }
}