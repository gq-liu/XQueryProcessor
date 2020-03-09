import XQUERYgen.XQUERYLexer;
import XQUERYgen.XQUERYParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * The XQuery Optimizer has
 */
class VarTreeNode {
    String var;
    List<VarTreeNode> children;
    public VarTreeNode(String var) {
        this.var = var;
        this.children = new ArrayList<>();
    }
}
public class XQueryOptimizer {
    public static InputStream optimize(InputStream in) throws IOException {
        // read in the XQuery text & get the xq context
        ANTLRInputStream input = new ANTLRInputStream(in);
        XQUERYLexer xqueryLexer = new XQUERYLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xqueryLexer);
        XQUERYParser xqueryParser = new XQUERYParser(commonTokenStream);
        XQUERYParser.XqContext xqContext = xqueryParser.xq();

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

        // analyse for clause
        analysisForClause(valueMap, forest, forClauseContext);

        // analysis where clause
        analysisWhereClause(forest, joinPart, whereClauseContext.cond());

        // rewrite for & where clause
        String forWhereClause = reformForWhere(valueMap, forest, joinPart, tupleMap);

        // rewrite return clause
        String returnClause = reformReturn(returnClauseContext, tupleMap);

        // output reformed XQuery
        String reformedXQuery = forWhereClause + returnClause;
        System.out.println(reformedXQuery);
        return new ByteArrayInputStream(reformedXQuery.getBytes());
    }

    private static void analysisForClause(Map<String, String> valueMap, Map<String, VarTreeNode> forest, XQUERYParser.ForClauseContext forClauseContext) {
        int varNum = forClauseContext.Var().size();
        for (int i = 0; i < varNum; i++) {
            String var = forClauseContext.Var(i).getText();
            String xq = forClauseContext.xq(i).getText();
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

    private static String reformForWhere(Map<String, String> valueMap, Map<String, VarTreeNode> forest, Map<String, List<String>> joinPart, Map<String, String> tupleMap) {
        // separate join group
        List<List<String>> joinGroups = separateJoinGroup(joinPart);
        Collections.reverse(joinGroups.get(0));
        System.out.println(joinGroups);

        Set<String> seen = new HashSet<>();
        StringBuilder result = new StringBuilder();
        for (int k = 0; k < joinGroups.size(); k++) {

            List<String> group = joinGroups.get(k);
            String tuple = "tuple" + k;
            StringBuilder partResult = new StringBuilder();

            for (String joinTables : group) {
                List<String> joinKWs = joinPart.get(joinTables);
//                System.out.println(joinTables);
                String[] tables = joinTables.split("&");
                if (tables.length == 1) {
                    continue;
                }

                String forPart1 = null;
                String forPart2 = null;
                StringBuilder filterPart1 = new StringBuilder();
                StringBuilder filterPart2 = new StringBuilder();
                String returnPart1 = null;
                String returnPart2 = null;

                if (!seen.contains(tables[0])) {
                    seen.add(tables[0]);
                    List<String> varibleList = getVaribleList(forest.get(tables[0]));
                    buildTupleVarMap(varibleList, tuple, tupleMap);
                    forPart1 = formInnerForClause(valueMap, varibleList);
                    if (joinPart.containsKey(tables[0])) {
                        filterPart1.append(joinPart.get(tables[0]).get(0));
                        for (int i = 1; i < joinPart.get(tables[0]).size(); i++) {
                            filterPart1.append(" and " + joinPart.get(tables[0]).get(i));
                        }
                    }
                    returnPart1 = formInnerReturnClause(varibleList, tuple);
                }
                if (!seen.contains(tables[1])) {
                    seen.add(tables[1]);
                    List<String> varibleList = getVaribleList(forest.get(tables[1]));
                    buildTupleVarMap(varibleList, tuple, tupleMap);
                    forPart2 = formInnerForClause(valueMap, varibleList);
                    if (joinPart.containsKey(tables[1])) {
                        filterPart2.append(joinPart.get(tables[1]).get(0));
                        for (int i = 1; i < joinPart.get(tables[1]).size(); i++) {
                            filterPart2.append(" and " + joinPart.get(tables[1]).get(i));
                        }
                    }
                    returnPart2 = formInnerReturnClause(varibleList, tuple);
                }
                if (forPart1 != null && forPart2 != null) {
                    partResult.append("join ( \n")
                            .append("for " + forPart1 + "\n")
                            .append(filterPart1.length() == 0 ? "" : "where " + filterPart1.toString() + "\n")
                            .append(returnPart1 + ",\n")
                            .append("for " + forPart2 + "\n")
                            .append(filterPart2.length() == 0 ? "" : "where " + filterPart2.toString() + "\n")
                            .append(returnPart2 + ",\n")
                            .append(getJoinCond(joinKWs, false) + "\n")
                            .append(")");
                } else if (forPart1 != null) {
                    partResult.insert(0, "join ( \n");
                    partResult.append(", \n")
                            .append("for " + forPart1 + '\n')
                            .append(filterPart1.length() == 0 ? "" : "where " + filterPart1.toString() + "\n")
                            .append(returnPart1 + ",\n")
                            .append(getJoinCond(joinKWs, true) + "\n")
                            .append(")\n");
                } else if (forPart2 != null) {
                    partResult.insert(0, "join ( \n");
                    partResult.append(", \n")
                            .append("for " + forPart2 + "\n")
                            .append(filterPart2.length() == 0 ? "" : "where " + filterPart2.toString() + "\n")
                            .append(returnPart2 + ",\n")
                            .append(getJoinCond(joinKWs, false) + "\n")
                            .append(")\n");
                } else {
                    // todo
                }
            }
            partResult.insert(0, "$" + tuple + " in ");
            result.append(partResult + ",\n");
        }
        result.insert(0, "for ");
        result.delete(result.length() - 2, result.length());

        // append for clause that need not to be joined
        StringBuilder whereClause = new StringBuilder();
        for (String root : forest.keySet()) {
            if (!seen.contains(root)) {
                List<String> varibleList = getVaribleList(forest.get(root));
                if (joinPart.containsKey(root)) {
                    whereClause.append(joinPart.get(root).get(0));
                    for (int i = 0; i < joinPart.get(root).size(); i++) {
                        whereClause.append(joinPart.get(root).get(i) + " and ");
                    }
                }
                result.append(formInnerForClause(valueMap, varibleList));
            }
        }
        if (whereClause.length() != 0) {
            result.append(whereClause.substring(0, whereClause.length() - 5));
        }
        return result.toString();
    }

    private static List<List<String>> separateJoinGroup(Map<String, List<String>> joinPart) {
        List<List<String>> joinGroups = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        List<String> joinTables = new ArrayList<>(joinPart.keySet());
        for (int i = 0; i < joinTables.size(); i++) {
            if (seen.contains(joinTables.get(i))) { continue; }
            List<String> group = new ArrayList<>();
            group.add(joinTables.get(i));
            seen.add(joinTables.get(i));
            String[] tables = joinTables.get(i).split("&");
            Set<String> keys = new HashSet<>();
            keys.add(tables[0]);
            if (tables.length == 2) { keys.add(tables[1]); }
            int start = i + 1;
            for (int j = start; j < joinTables.size(); j++) {
                if (seen.contains(joinTables.get(j))) { continue; }
                if (shareTable(keys, joinTables.get(j))) {
                    int keysSizePrev = keys.size();
                    String[] tables2 = joinTables.get(j).split("&");
                    keys.add(tables2[0]);
                    if (tables2.length > 1) { keys.add(tables2[1]); }
                    group.add(joinTables.get(j));
                    seen.add(joinTables.get(j));
                    if (keys.size() > keysSizePrev) { j = start - 1; }
                }
            }
            joinGroups.add(group);
        }
        return joinGroups;
    }

    private static boolean shareTable(Set<String> keys, String joinTables) {
        String[] tables = joinTables.split("&");
        return keys.contains(tables[0]) || (tables.length > 1 && keys.contains(tables[1]));
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

    private static String reformReturn(XQUERYParser.ReturnClauseContext returnClauseContext, Map<String, String> tupleMap) {
        Set<String> varToReplace = new HashSet<>();
        String returnClause = returnClauseContext.getText();
        findVarToReplace(returnClauseContext.getChild(1), varToReplace);
        for (String var : varToReplace) {
            returnClause = returnClause.replaceAll("\\$" + var,   "\\$" + tupleMap.get(var) + "/" + var + "/*");
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

//    private static void printNode(VarTreeNode root) {
//        Queue<VarTreeNode> queue = new LinkedList<>();
//        queue.offer(root);
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            while (size > 0) {
//                size--;
//                VarTreeNode node = queue.poll();
////                System.out.print(node.var + " ");
//                for (VarTreeNode child : node.children) {
//                    queue.offer(child);
//                }
//            }
////            System.out.println();
//        }
//    }

    public static void main(String[] args) throws Exception {
        String inputPath = args[0];
        //String inputPath = "./testFiles/test21.txt";
        InputStream inputStream = new FileInputStream(inputPath);
        optimize(inputStream);
    }
}
