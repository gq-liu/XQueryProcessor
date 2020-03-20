import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import XQUERYgen.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

public class XQueryProcessorOPT {
    public static void main(String[] args) throws Exception {


        String inputPath = args[0];
        String joinBias = args[1];
        //String inputPath = "./testFiles/test21.txt";
        InputStream inputStream = new FileInputStream(inputPath);
        // optimization
        InputStream inputStreamOPT = XQueryOptimizer.optimize(inputStream, joinBias);
        // execute XQuery
        ANTLRInputStream input = new ANTLRInputStream(inputStreamOPT);
        XQUERYLexer xqueryLexer = new XQUERYLexer(input);

        CommonTokenStream commonTokenStream = new CommonTokenStream(xqueryLexer);
        XQUERYParser xqueryParser = new XQUERYParser(commonTokenStream);
        xqueryLexer.removeErrorListeners();
        ParseTree parseTree = xqueryParser.xq();

        MyXqueryVisitor myXqueryVisitor = new MyXqueryVisitor();
        LinkedList<Node> result = myXqueryVisitor.visit(parseTree);
        if (result == null) { throw new Exception("Invalid XPath Expression!"); }
        System.out.println("result size: " + result.size());
        for (Node node : result) {
            if (node != null) printNode(node, "");
            System.out.println();
        }
    }
    private static void printNode(Node node, String tab) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print(tab + "<" + node.getNodeName().toUpperCase() + ">");
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null && nodeMap.getLength() != 0) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    printNode(nodeMap.item(i), tab + "  ");
                }
            }
            NodeList nodeList = node.getChildNodes();
            if (nodeList != null) {
                if (nodeList.getLength() == 1 && nodeList.item(0).getNodeType() == Node.TEXT_NODE) {
                    System.out.print(node.getTextContent());
                    System.out.print("</" + node.getNodeName().toUpperCase() + ">");
                } else {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        System.out.println();
                        printNode(nodeList.item(i), tab + "  ");
                    }
                    System.out.println();
                    System.out.print(tab + "</" + node.getNodeName().toUpperCase() + ">");
                }
            }


        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            System.out.println(tab + "<@" + node.getNodeName() + "=" + node.getNodeValue() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            System.out.println(tab + node.getTextContent());
        } else if (node.getNodeType() == Node.DOCUMENT_NODE) {
            System.out.println("DOCUMENT_NODE");
        }
    }
}
