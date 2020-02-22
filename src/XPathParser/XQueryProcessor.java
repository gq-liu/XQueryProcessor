import XPATHgen.XPATHLexer;
import XPATHgen.XPATHParser;
import XQUERYgen.XQUERYLexer;
import XQUERYgen.XQUERYParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

public class XQueryProcessor {
    public static void main(String[] args) throws Exception {


        String XPath = args[0];
        String inputPath = "/Users/liuguoqiang/Study/20win/cse232b/cse232bProject/testFiles/test1.txt";
        String resultPath = "./result.xml";
        //InputStream XPathStream = new ByteArrayInputStream(XPath.getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = new FileInputStream(inputPath);
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        XQUERYLexer xqueryLexer = new XQUERYLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xqueryLexer);
        XQUERYParser xqueryParser = new XQUERYParser(commonTokenStream);
        xqueryLexer.removeErrorListeners();
        ParseTree parseTree = xqueryParser.xq();

        MyXqueryVisitor myXqueryVisitor = new MyXqueryVisitor();
        LinkedList<Node> result = myXqueryVisitor.visit(parseTree);
        if (result == null) { throw new Exception("Invalid XPath Expression!"); }
        for (Node node : result) {
            if (node != null) printNode(node, "");
        }

        //createResultFile(myXqueryVisitor.inputDoc, result, resultPath);

    }

    public static void createResultFile(Document doc, LinkedList<Node> finalResult, String resultPath) {
        doc.appendChild(finalResult.get(0));
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(resultPath);
            transformer.transform(source, res);
        } catch (Exception e) {
            e.printStackTrace();
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
