import XQUERYgen.XQUERYBaseVisitor;
import XQUERYgen.XQUERYParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class MyXqueryVisitor extends XQUERYBaseVisitor<LinkedList<Node>> {
    private Document inputDoc = null;
    private HashMap<String, LinkedList<Node>> variableMap = new HashMap<>();
    //private Stack<HashMap<String, LinkedList<Node>>> contextStack = new Stack<>();
    private LinkedList<Node> context = new LinkedList<>();

    /* XQuery Visitor */
    @Override
    public LinkedList<Node> visitXqTag(XQUERYParser.XqTagContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        String tagName = ctx.tagName(0).ID().getText();
        LinkedList<Node> nodes = visit(ctx.xq());
        Node node = makeNode(tagName, nodes);
        result.add(node);
        return result;
    }

    @Override
    public LinkedList<Node> visitXqAp(XQUERYParser.XqApContext ctx) {
        return visit(ctx.ap());
    }

    @Override
    public LinkedList<Node> visitXqVar(XQUERYParser.XqVarContext ctx) {
        String var = ctx.Var().getText();
        return variableMap.get(var);
    }

    @Override
    public LinkedList<Node> visitXqSingleSlash(XQUERYParser.XqSingleSlashContext ctx) {
        LinkedList<Node> result = visit(ctx.xq());
        LinkedList<Node> temp = new LinkedList<>();
        for (Node node : result) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                temp.add(nodeList.item(i));
            }
        }
        context = temp;
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitXqDoubleSlash(XQUERYParser.XqDoubleSlashContext ctx) {
        LinkedList<Node> result = visit(ctx.xq());
        LinkedList<Node> temp = new LinkedList<>();
        for (Node node : result) {
            temp.addAll(getDescenders(node));
        }
        context = temp;
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitXqParentheses(XQUERYParser.XqParenthesesContext ctx) {
        return visit(ctx.xq());
    }

    @Override
    public LinkedList<Node> visitXqStringConstant(XQUERYParser.XqStringConstantContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        String name = ctx.StringConstant().getText();
        Node stringConstantNode = inputDoc.createTextNode(name.substring(1, name.length() - 1));
        result.add(stringConstantNode);
        return result;
    }

    @Override
    public LinkedList<Node> visitXqLet(XQUERYParser.XqLetContext ctx) {
        HashMap<String, LinkedList<Node>> tempContextMap = new HashMap<>(variableMap);
        visit(ctx.letClause());
        LinkedList<Node> result = visit(ctx.xq());
        variableMap = tempContextMap;
        return result;
    }


    @Override
    public LinkedList<Node> visitXqComma(XQUERYParser.XqCommaContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        //LinkedList<Node> contextTemp = new LinkedList<>();
        LinkedList<Node> left = visit(ctx.xq(0));
        //context = contextTemp;
        LinkedList<Node> right = visit(ctx.xq(1));
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    @Override
    public LinkedList<Node> visitXqFLWR(XQUERYParser.XqFLWRContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        HashMap<String, LinkedList<Node>> tempVariableMap = new HashMap<>(variableMap);
        FLWRHelper(ctx, 0, result);
        variableMap = tempVariableMap;
        return result;
    }

//    @Override
//    public LinkedList<Node> visitForClause(XQUERYParser.ForClauseContext ctx) {
//        return super.visitForClause(ctx);
//    }

    @Override
    public LinkedList<Node> visitLetClause(XQUERYParser.LetClauseContext ctx) {
        int numVar = ctx.Var().size();
        for (int i = 0; i < numVar; i++) {
            String varName = ctx.Var(i).getText();
            variableMap.put(varName, visit(ctx.xq(i)));
        }
        return null;
    }

    @Override
    public LinkedList<Node> visitWhereClause(XQUERYParser.WhereClauseContext ctx) {
        return visit(ctx.cond());
    }

    @Override
    public LinkedList<Node> visitReturnClause(XQUERYParser.ReturnClauseContext ctx) {
        LinkedList<Node> result = visit(ctx.xq());
        return result;
    }

    @Override
    public LinkedList<Node> visitCondEmpty(XQUERYParser.CondEmptyContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> tempRes = visit(ctx.xq());
        if (tempRes.isEmpty()) {
            Node specifyEmpty = inputDoc.createElement("specifyEmpty");
            result.add(specifyEmpty);
        }
        return result;
    }

    @Override
    public LinkedList<Node> visitCondEq(XQUERYParser.CondEqContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> left = visit(ctx.xq(0));
        LinkedList<Node> right = visit(ctx.xq(1));
        if (left == null || right == null || left.isEmpty() || right.isEmpty()) { return result; }
        for (Node leftNode : left) {
            for (Node rightNode : right) {
                if (leftNode.isEqualNode(rightNode)) {
                    result.add(leftNode);
                    break;
                }
            }
        }
        return result;
    }

    // need check
    @Override
    public LinkedList<Node> visitCondIs(XQUERYParser.CondIsContext ctx) {
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : contextTemp) {
            LinkedList<Node> temp = new LinkedList<Node>();
            temp.add(node);
            // find left;
            context = temp;
            LinkedList<Node> left = visit(ctx.xq(0));
            context = temp;
            LinkedList<Node> right = visit(ctx.xq(1));
            boolean hasFound = false;
            for (Node leftNode : left) {
                for (Node rightNode : right) {
                    if (leftNode.isSameNode(rightNode)) {
                        hasFound = true;
                        break;
                    }
                }
                if (hasFound) { break; }
            }
            if (hasFound && !result.contains(node)) {
                result.add(node);
            }
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitCondSatisfy(XQUERYParser.CondSatisfyContext ctx) {
        HashMap<String, LinkedList<Node>> tempVariableMap = new HashMap<>(variableMap);
        int varNum = ctx.Var().size();
        for (int i = 0; i < varNum; i++) {
            String varName = ctx.Var(i).getText();
            LinkedList<Node> varNodes = visit(ctx.xq(i));
            variableMap.put(varName, varNodes);
        }
        LinkedList<Node> result = visit(ctx.cond());
        variableMap = tempVariableMap;
        return result;
    }

    @Override
    public LinkedList<Node> visitCondAND(XQUERYParser.CondANDContext ctx) {
//        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> list0 = visit(ctx.cond(0));
//        context = contextTemp;
        LinkedList<Node> list1 = visit(ctx.cond(1));
        if (!list0.isEmpty() && !list1.isEmpty()) {
            Node specifyTrue = inputDoc.createElement("specifyTrue");
            result.add(specifyTrue);
        }
        return result;
    }

    @Override
    public LinkedList<Node> visitCondOR(XQUERYParser.CondORContext ctx) {

//        LinkedList<Node> contextTemp = context;
        LinkedList<Node> list0 = visit(ctx.cond(0));
//        context = contextTemp;
        LinkedList<Node> list1 = visit(ctx.cond(1));
        for (Node n : list1) {
            if (!list0.contains(n)) {list0.add(n);}
        }
//        context = list0;
        return list0;

//        LinkedList<Node> result = new LinkedList<>();
//        LinkedList<Node> tempContext = context;
//        LinkedList<Node> left = visit(ctx.cond(0));
//        context = tempContext;
//        LinkedList<Node> right = visit(ctx.cond(1));
//        context = tempContext;
//        result.addAll(left);
//        result.addAll(right);
//        return result;
    }

    @Override
    public LinkedList<Node> visitCondComma(XQUERYParser.CondCommaContext ctx) {
        return visit(ctx.cond());
    }

    @Override
    public LinkedList<Node> visitCondNOT(XQUERYParser.CondNOTContext ctx) {
        // need to store a temp context since when call visit(ctx.f()), context will be changed

//        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> excludeNodes = visit(ctx.cond());

        if (excludeNodes.isEmpty()) {
            Node specifyEmpty = inputDoc.createElement("specifyEmpty");
            result.add(specifyEmpty);
        }
//        } else {
//            for (Node n : contextTemp) { // need use contextTemp since context has changed
//                if (!excludeNodes.contains(n)) {
//                    result.add(n);
//                }
//            }
//      }
//        update context
//        context = result;
//        context = contextTemp;
        return result;
    }

    /* XPath Visitor */
    @Override
    public LinkedList<Node> visitDoc(XQUERYParser.DocContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        if (inputDoc == null) {
            // create the file
            File xmlFile = new File(ctx.filename().getText());
            // convert XML file to DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = null;

            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            if (db != null) {
                try {
                    inputDoc = db.parse(xmlFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (inputDoc != null) {
                inputDoc.getDocumentElement().normalize();
            }
        }

        result.add(inputDoc);
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitApDouble(XQUERYParser.ApDoubleContext ctx) {
        // visit the doc node to parse XML to DOM
        visit(ctx.doc());

        // get all descenders from each node in context
        LinkedList<Node> descendents = new LinkedList<>();
        descendents.addAll(getDescenders(context.get(0)));

        // add all these nodes into context
        context.addAll(descendents);
        LinkedList<Node> result = visit(ctx.rp());
        return result;
    }

    @Override
    public LinkedList<Node> visitApSingle(XQUERYParser.ApSingleContext ctx) {
        // visit the doc node to parse XML to DOM
        visit(ctx.doc());
        LinkedList<Node> result = visit(ctx.rp());
        return result;
    }

    @Override
    public LinkedList<Node> visitRpSingleSlash(XQUERYParser.RpSingleSlashContext ctx) {
        // get the corresponding context after visit(ctx.rp(0))
        visit(ctx.rp(0));
        LinkedList<Node> temp = new LinkedList<>();
        for (Node node : context) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                temp.add(nodeList.item(i));
            }
        }
        context = temp;
        LinkedList<Node> result = visit(ctx.rp(1));
        return result;
    }

    @Override
    public LinkedList<Node> visitRpDoubleSlash(XQUERYParser.RpDoubleSlashContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        visit(ctx.rp(0));
        for (Node node : context) {
            result.addAll(getDescenders(node));
        }
        context = result;
        return visit(ctx.rp(1));
    }

    @Override
    public LinkedList<Node> visitRpText(XQUERYParser.RpTextContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        for (Node node : context) {
            if (node.getNodeType() == Node.TEXT_NODE) {
                result.add(node);
            }
        }
        context = result;
        return result;
//        LinkedList<Node> children = getChildren();
//        LinkedList<Node> result = new LinkedList<>();
//        for (Node node : children) {
//            if (node.getNodeType() == Node.TEXT_NODE) {
//                result.add(node);
//            }
//        }
//        context = result;
//        return result;
    }

    @Override
    public LinkedList<Node> visitRpAttri(XQUERYParser.RpAttriContext ctx) {
        String attName = ctx.attName().getText();
        LinkedList<Node> children = getChildren();
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : children) {
            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                if (node.getNodeName().equals(attName)) {
                    result.add(node);
                }
            }
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpTag(XQUERYParser.RpTagContext ctx) {
        // CTM BUG IS HERE
        String tagName = ctx.tagName().ID().getText();
        LinkedList<Node> result = new LinkedList<>();
        // get children of each node in context
        //LinkedList<Node> children = getChildren();
        for (Node node : context) {
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tagName)) {
                result.add(node);
            }
        }
        // update context
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpParentheses(XQUERYParser.RpParenthesesContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitRpComma(XQUERYParser.RpCommaContext ctx) {
        // store the current context
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        // get the result from left rp
        result.addAll(visit(ctx.rp(0)));
        // restore the context
        // restore te context
        context = contextTemp;
        // get the result from right rp
        result.addAll(visit(ctx.rp(1)));
        // update the result
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpParent(XQUERYParser.RpParentContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : context) {
            if (node == null || node.getParentNode() == null
                    || result.contains(node.getParentNode().getParentNode())) { continue; }
            result.add(node.getParentNode().getParentNode());
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpChildren(XQUERYParser.RpChildrenContext ctx) {
        // LinkedList<Node> result = getChildren();
        // context = result;
        //return result;
        return context;
    }

    @Override
    public LinkedList<Node> visitRpSelf(XQUERYParser.RpSelfContext ctx) {
        return context;
    }

    @Override
    public LinkedList<Node> visitRpCondition(XQUERYParser.RpConditionContext ctx) {
        visit(ctx.rp());
        LinkedList<Node> result = visit(ctx.f());
        // update the context
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterRp(XQUERYParser.FilterRpContext ctx) {
        // filter with relative path
        // store the context
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();

        // for each node in context, check whether it is satisfied the condition
        for (Node node : contextTemp) {
            // clear is O(N)
            LinkedList<Node> oneNodeContext = new LinkedList<Node>();
            oneNodeContext.add(node);
            context = oneNodeContext;
            LinkedList<Node> tempRes = visit(ctx.rp());
            if (!tempRes.isEmpty()) {  // if it is NOT empty, the node is satisfied the condition
                result.add(node);
            }
        }
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterEq(XQUERYParser.FilterEqContext ctx) {
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : contextTemp) {
            LinkedList<Node> temp = new LinkedList<Node>();
            temp.add(node);
            // find left
            context = temp;
            LinkedList<Node> left = visit(ctx.rp(0));
            // find right
            context = temp;
            LinkedList<Node> right = visit(ctx.rp(1));
            boolean hasFound = false;
            for (Node leftNode : left) {
                for (Node rightNode : right) {
                    if (leftNode.isEqualNode(rightNode)) {
                        hasFound = true;
                        break;
                    }
                }
                if (hasFound) { break; }
            }
            // if hasFind
            if (hasFound && !result.contains(node)) {
                result.add(node);
            }
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterIs(XQUERYParser.FilterIsContext ctx) {
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : contextTemp) {
            LinkedList<Node> temp = new LinkedList<Node>();
            temp.add(node);
            // find left;
            context = temp;
            LinkedList<Node> left = visit(ctx.rp(0));
            context = temp;
            LinkedList<Node> right = visit(ctx.rp(1));
            boolean hasFound = false;
            for (Node leftNode : left) {
                for (Node rightNode : right) {
                    if (leftNode.isSameNode(rightNode)) {
                        hasFound = true;
                        break;
                    }
                }
                if (hasFound) { break; }
            }
            if (hasFound && !result.contains(node)) {
                result.add(node);
            }
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterParentheses(XQUERYParser.FilterParenthesesContext ctx) {
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitFilterOR(XQUERYParser.FilterORContext ctx) {
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> list0 = visit(ctx.f(0));
        context = contextTemp;
        LinkedList<Node> list1 = visit(ctx.f(1));
        for (Node n : list1) {
            if (!list0.contains(n)) {list0.add(n);}
        }
        context = list0;
        return list0;
    }

    @Override
    public LinkedList<Node> visitFilterAND(XQUERYParser.FilterANDContext ctx) {
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        LinkedList<Node> list0 = visit(ctx.f(0));
        context = contextTemp;
        LinkedList<Node> list1 = visit(ctx.f(1));
        for (Node n : list0) {
            if (list1.contains(n)) {result.add(n);}
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterNOT(XQUERYParser.FilterNOTContext ctx) {
        // need to store a temp context since when call visit(ctx.f()), context will be changed
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        LinkedList<Node> excludeNodes = visit(ctx.f());

        if (excludeNodes.isEmpty()) {
            result = contextTemp;
        } else {
            for (Node n : contextTemp) { // need use contextTemp since context has changed
                if (!excludeNodes.contains(n)) {
                    result.add(n);
                }
            }
        }
        // update context
        context = result;
        return result;
    }



    /* private function */

    // return all children of context nodes;
    private LinkedList<Node> getChildren() {
        LinkedList<Node> result = new LinkedList<>();
        for (Node node : context) {
            // only ELEMENT_NODE and DOCUMENT_NODE has children
            if (node == null || node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.DOCUMENT_NODE) { continue; }
            NodeList nodeList = node.getChildNodes();
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    result.add(nodeMap.item(i));
                }
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                result.add(nodeList.item(i));
            }
        }
        return result;
    }

    // return all descenders of current node (exclude itself)
    private LinkedList<Node> getDescenders(Node node) {
        LinkedList<Node> res = new LinkedList<>();
        NodeList nodeList = node.getChildNodes();

        NamedNodeMap nodeMap = node.getAttributes();
        if (nodeMap != null) {
            for (int i = 0; i < nodeMap.getLength(); i++) {
                res.add(nodeMap.item(i));
            }
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node curr = nodeList.item(i);
            res.add(curr);
            LinkedList<Node> tempList = getDescenders(curr);
            res.addAll(tempList);
        }
        return res;
    }


    private void FLWRHelper(XQUERYParser.XqFLWRContext ctx, int i, LinkedList<Node> result) {
        int numVar = ctx.forClause().Var().size();
        if (i < numVar) {
            String var = ctx.forClause().Var(i).getText();
            LinkedList<Node> nodes = visit(ctx.forClause().xq(i));
            for (int k = 0; k < nodes.size(); k++) {
            //for (Node node : nodes) {
                variableMap.remove(var);
                LinkedList<Node> varValue = new LinkedList<>();
                //varValue.add(node);
                varValue.add(nodes.get(k));
                variableMap.put(var, varValue);
                FLWRHelper(ctx, i + 1, result);
            }
        } else {
            HashMap<String, LinkedList<Node>> oldContextMap = new HashMap<>(variableMap);
            if (ctx.letClause() != null) {
                visit(ctx.letClause());
            }
            if (ctx.whereClause() != null) {
                if (visit(ctx.whereClause()).size() == 0) {
                    return;
                }
            }
            LinkedList<Node> partResult = visit(ctx.returnClause());
            if (partResult != null) {
                result.addAll(partResult);
            }
            variableMap = oldContextMap;
        }
    }

    private Node makeNode(String tagName, LinkedList<Node> subNodes) {
        Node node = inputDoc.createElement(tagName);
        for (Node curr : subNodes) {
            if (curr != null) {
                // copy from the original node (deep copy)
                Node child = inputDoc.importNode(curr, true);
                node.appendChild(child);
            }
        }
        return node;
    }
}
