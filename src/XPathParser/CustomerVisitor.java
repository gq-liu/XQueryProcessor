import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.LinkedList;

public class CustomerVisitor extends XPATHBaseVisitor<LinkedList<Node>>{
    //
    LinkedList<Node> context = new LinkedList<Node>();
    Document doc = null;

    @Override
    // parse XML file to DOM and maintain in the memory
    public LinkedList<Node> visitDoc(XPATHParser.DocContext ctx) {

        // create the file
        File xmlFile = new File(ctx.filename().getText());
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
    @Override
    public LinkedList<Node> visitApDouble(XPATHParser.ApDoubleContext ctx) {

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

    @Override
    public LinkedList<Node> visitApSingle(XPATHParser.ApSingleContext ctx) {
        // visit the doc node to parse XML to DOM
        visit(ctx.doc());
        LinkedList<Node> result = visit(ctx.rp());
        return result;
    }

    @Override
    public LinkedList<Node> visitRpSingleSlash(XPATHParser.RpSingleSlashContext ctx) {
        // get the corresponding context after visit(ctx.rp(0))
        visit(ctx.rp(0));
        LinkedList<Node> result = visit(ctx.rp(1));
        return result;
    }

    @Override
    public LinkedList<Node> visitRpDoubleSlash(XPATHParser.RpDoubleSlashContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        visit(ctx.rp(0));
        for (Node node : context) {
            result.addAll(getDescenders(node));
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpText(XPATHParser.RpTextContext ctx) {
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
    @Override
    public LinkedList<Node> visitRpAttri(XPATHParser.RpAttriContext ctx) {
        String attName = ctx.attName().getText();
        LinkedList<Node> children = getChildren();
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : children) {
            if (node.getNodeType() == Node.ATTRIBUTE_NODE && node.getTextContent().equals(attName)) {
                result.add(node);
            }
        }
        context = result;
        return result;
    }



    @Override
    public LinkedList<Node> visitRpTag(XPATHParser.RpTagContext ctx) {
        String tagName = ctx.tagName().ID().getText();
        System.out.println("TagName: " + tagName);
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

    @Override
    public LinkedList<Node> visitRpParentheses(XPATHParser.RpParenthesesContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitRpComma(XPATHParser.RpCommaContext ctx) {
        // store the current context
        LinkedList<Node> contextTemp = context;
        LinkedList<Node> result = new LinkedList<Node>();
        // get the result from left rp
        result.addAll(visit(ctx.rp(0)));
        // restore the context
        context = contextTemp;
        // get the result from right rp
        result.addAll(visit(ctx.rp(2)));
        // update the result
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpParent(XPATHParser.RpParentContext ctx) {
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : context) {
            result.add(node.getParentNode());
        }
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpChildren(XPATHParser.RpChildrenContext ctx) {
        LinkedList<Node> result = getChildren();
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitRpSelf(XPATHParser.RpSelfContext ctx) {
        return context;
    }



    @Override
    public LinkedList<Node> visitRpCondition(XPATHParser.RpConditionContext ctx) {
        visit(ctx.rp());
        // context ????
        LinkedList<Node> result = visit(ctx.f());
        context = result;
        return result;
    }

    @Override
    public LinkedList<Node> visitFilterRp(XPATHParser.FilterRpContext ctx) {

    }

    // return all children of context nodes;
    private LinkedList<Node> getChildren() {
        LinkedList<Node> result = new LinkedList<Node>();
        for (Node node : context) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < context.size(); i++) {
                result.add(nodeList.item(i));
            }
        }
        return result;
    }

    // return all descenders of current node (exclude itself)
    private LinkedList<Node> getDescenders(Node node) {
        LinkedList<Node> res = new LinkedList<Node>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node curr = nodeList.item(i);
            res.add(curr);
            LinkedList<Node> tempList = getDescenders(curr);
            res.addAll(tempList);
        }
        return res;
    }
}
