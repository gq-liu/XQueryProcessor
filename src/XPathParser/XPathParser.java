import XPATHgen.XPATHLexer;
import XPATHgen.XPATHParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

public class XPathParser {
    public static void main(String[] args) throws Exception {
        String filename = "/Users/mac126/19fall/udemy/spring/cse232bProject/testFiles/test1.txt";
        InputStream in = new FileInputStream(filename);
        ANTLRInputStream input = new ANTLRInputStream(in);
        XPATHLexer xPathLexer = new XPATHLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xPathLexer);
        XPATHParser xpathParser = new XPATHParser(commonTokenStream);
        xpathParser.removeErrorListeners();
        ParseTree parseTree = xpathParser.ap();

        CustomerVisitor customerVisitor = new CustomerVisitor();
        LinkedList<Node> result = customerVisitor.visit(parseTree);
        System.out.println(result.size());
        // output
        for (Node node : result) {
            printNode(node, "");
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
        }
    }
}
