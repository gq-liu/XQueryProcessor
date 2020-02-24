import XPATHgen.XPATHLexer;
import XPATHgen.XPATHParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class XPathParser {
    public static void main(String[] args) throws Exception {

        String XPath = args[0];
        InputStream XPathStream = new ByteArrayInputStream(XPath.getBytes(StandardCharsets.UTF_8));
        ANTLRInputStream input = new ANTLRInputStream(XPathStream);
        XPATHLexer xPathLexer = new XPATHLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xPathLexer);
        XPATHParser xpathParser = new XPATHParser(commonTokenStream);
        xpathParser.removeErrorListeners();
        ParseTree parseTree = xpathParser.ap();

        CustomerVisitor customerVisitor = new CustomerVisitor();
        LinkedList<Node> result = customerVisitor.visit(parseTree);
        if (result == null) { throw new Exception("Invalid XPath Expression!"); }
        System.out.println("Result Size:" + result.size());
        for (Node node : result) {
            if (node != null) printNode(node, "");
        }
    }

    private static void printNode(Node node, String tab) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(tab + "<" + node.getNodeName() + ">");
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    printNode(nodeMap.item(i), tab + "  ");
                }
            }
            NodeList nodeList = node.getChildNodes();
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    printNode(nodeList.item(i), tab + "  ");
                }
            }
            System.out.println(tab + "<" + node.getNodeName() + "/>");

        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            System.out.println(tab + "<@" + node.getNodeName() + "=" + node.getNodeValue() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            System.out.println(tab + node.getTextContent());
        } else if (node.getNodeType() == Node.DOCUMENT_NODE) {
            System.out.println("DOCUMENT_NODE");
        }
    }
}
