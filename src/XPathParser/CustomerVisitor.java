import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.LinkedList;

public class CustomerVisitor extends XPathParser.XPATHgen.XPATHBaseVisitor<LinkedList<Node>> {
    //
    LinkedList<Node> context = new LinkedList<Node>();
    Document doc = null;

    // parse XML file to DOM and maintain in the memory
    public LinkedList<Node> visitDoc(XPathParser.XPATHgen.XPATHParser.DocContext ctx) {

        // create the file
        File xmlFile = new File(ctx.filename().getText());
        System.out.println(xmlFile.getAbsolutePath());
        // convert XML file to DOM
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = null;
        LinkedList<Node> result = new LinkedList<Node>();

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (db != null) {
            try {
                doc = db.parse(xmlFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        result.add(doc);
        context = result;
        return result;

    }

    //
    public LinkedList<Node> visitApDouble(XPathParser.XPATHgen.XPATHParser.ApDoubleContext ctx) {
        // visit the doc node to parse XML to DOM
        visit(ctx.doc());
        // get all descenders from each node in context
        LinkedList<Node> descendents = new LinkedList<Node>();
        descendents.addAll(getDescenders(context.get(0)));
//        for (int i = 0; i < context.size(); i++) {
//            descendents.addAll(getDescenders(context.get(i)));
//        }
        // add all these nodes into context
        context.addAll(descendents);
        LinkedList<Node> result = visit(ctx.rp());
        return result;
    }

    public LinkedList<Node> visitApSingle(XPathParser.XPATHgen.XPATHParser.ApSingleContext ctx) {
        // visit the doc node to parse XML to DOM

        visit(ctx.doc());
        LinkedList<Node> result = visit(ctx.rp());
        return result;
    }

    public LinkedList<Node> visitRpSingleSlash(XPathParser.XPATHgen.XPATHParser.RpSingleSlashContext ctx) {
        // get the corresponding context after visit(ctx.rp(0))
        visit(ctx.rp(0));
        LinkedList<Node> result = visit(ctx.rp(1));
        return result;
    }

    public LinkedList<Node> visitRpDoubleSlash(XPathParser.XPATHgen.XPATHParser.RpDoubleSlashContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        visit(ctx.rp(0));
        for (Node node : context) {
            result.addAll(getDescenders(node));
        }
        context = result;
        return result;
    }

    public LinkedList<Node> visitRpText(XPathParser.XPATHgen.XPATHParser.RpTextContext ctx) {
        LinkedList<Node> children = getChildren();
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : children) {
            if (node.getNodeType() == Node.TEXT_NODE) {
                result.add(node);
            }
        }
        context = result;
        return result;
    }

    // 疑问？？？
    public LinkedList<Node> visitRpAttri(XPathParser.XPATHgen.XPATHParser.RpAttriContext ctx) {
        String attName = ctx.attName().getText();
//        System.out.println("Attribute Name: " + attName);
        LinkedList<Node> children = getChildren();
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : children) {
//            System.out.println(node.getNodeType() == Node.ATTRIBUTE_NODE);
            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                if (node.getNodeName().equals(attName)) {
                    result.add(node);
                }
            }
        }
        context = result;
        return result;
    }



    public LinkedList<Node> visitRpTag(XPathParser.XPATHgen.XPATHParser.RpTagContext ctx) {
        String tagName = ctx.tagName().ID().getText();
        LinkedList<Node> result = new LinkedList<Node>();
        // get children of each node in context
        LinkedList<Node> children = getChildren();
        for (Node node : children) {
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tagName)) {
                result.add(node);
            }
        }
        // update context
        context = result;
        return result;
    }

    public LinkedList<Node> visitRpParentheses(XPathParser.XPATHgen.XPATHParser.RpParenthesesContext ctx) {
        return visit(ctx.rp());
    }

    public LinkedList<Node> visitRpComma(XPathParser.XPATHgen.XPATHParser.RpCommaContext ctx) {
        // store the current context
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        // get the result from left rp
        result.addAll(visit(ctx.rp(0)));
        // restore the context
        context = contextTemp;
        // get the result from right rp
        result.addAll(visit(ctx.rp(1)));
        // update the result
        context = result;
        return result;
    }

    public LinkedList<Node> visitRpParent(XPathParser.XPATHgen.XPATHParser.RpParentContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : context) {
            if (!result.contains(node.getParentNode())) {
                result.add(node.getParentNode());
            }
        }
        context = result;
        return result;
    }

    public LinkedList<Node> visitRpChildren(XPathParser.XPATHgen.XPATHParser.RpChildrenContext ctx) {
        LinkedList<Node> result = getChildren();
        context = result;
        return result;
    }

    public LinkedList<Node> visitRpSelf(XPathParser.XPATHgen.XPATHParser.RpSelfContext ctx) {
        return context;
    }



    public LinkedList<Node> visitRpCondition(XPathParser.XPATHgen.XPATHParser.RpConditionContext ctx) {
        visit(ctx.rp());
        LinkedList<Node> result = visit(ctx.f());
        // update the context
        context = result;
        return result;
    }

    public LinkedList<Node> visitFilterRp(XPathParser.XPATHgen.XPATHParser.FilterRpContext ctx) {
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

    public LinkedList<Node> visitFilterEq(XPathParser.XPATHgen.XPATHParser.FilterEqContext ctx) {
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

    public LinkedList<Node> visitFilterIs(XPathParser.XPATHgen.XPATHParser.FilterIsContext ctx) {
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

    public LinkedList<Node> visitFilterParentheses(XPathParser.XPATHgen.XPATHParser.FilterParenthesesContext ctx) {
        return visit(ctx.f());
    }

    public LinkedList<Node> visitFilterOR(XPathParser.XPATHgen.XPATHParser.FilterORContext ctx) {
        LinkedList<Node> List_0 = visit(ctx.f(0)), List_1 = visit(ctx.f(1));
        for (Node n : List_1) {
            if (!List_0.contains(n)) {List_0.add(n);}
        }
        return List_0;
    }

    public LinkedList<Node> visitFilterAND(XPathParser.XPATHgen.XPATHParser.FilterANDContext ctx) {
        LinkedList<Node> List_0 = visit(ctx.f(0)), List_1 = visit(ctx.f(1)), res = new LinkedList<Node>();
        for (Node n : List_0) {
            if (List_1.contains(n)) {res.add(n);}
        }
        return res;
    }

    public LinkedList<Node> visitFilterNOT(XPathParser.XPATHgen.XPATHParser.FilterNOTContext ctx) {

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

//        LinkedList<Node> notList = visit(ctx.f());
//        if (notList.isEmpty()) {
//
//            return this.context;
//        } else {
//            LinkedList<Node> res =  new LinkedList<Node>();
//            for (Node n : this.context) {
//                if (!notList.contains(n)) {res.add(n);}
//            }
//            // need to update context
//            context = res;
//            return res;
//        }
    }

    // return all children of context nodes;
    private LinkedList<Node> getChildren() {
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : context) {
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
        LinkedList<Node> res = new LinkedList<Node>();
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

//    private boolean isValueEq(Node node1, Node node2) {
//        if (node1.getNodeType() != node2.getNodeType()) { return false; }
//
//        if (node1.getNodeType() == Node.ELEMENT_NODE
//                && node1.getNodeName().equals(node2.getNodeName())
//                && node2.getChildNodes().getLength() == node2.getChildNodes().getLength()
//                || node1.getNodeType() == Node.TEXT_NODE
//                && node1.getTextContent().equals(node2.getTextContent())
//                && node1.getChildNodes().getLength() == node2.getChildNodes().getLength()) {
//            NodeList children1 = node1.getChildNodes();
//            NodeList children2 = node2.getChildNodes();
//            for (int i = 0; i < children1.getLength(); i++) {
//                if (!isValueEq(children1.item(i), children2.item(i))) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
}
