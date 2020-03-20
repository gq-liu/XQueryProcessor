import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import XQUERYgen.*;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

public class XQueryProcessorOPT {
    public static void main(String[] args) throws Exception {


        String inputPath = args[0];
        String joinBias = args[1].toUpperCase();
        String outputXQueryPath = "output/optimizedXQuery.txt";
        String outputResultPath = "output/result.xml";
        if (args.length != 2 || !joinBias.equals("-B") && !joinBias.equals("B") && !joinBias.equals("-L") && !joinBias.equals("L")) {
            throw new Exception("Invalid Arguments! Example: java -jar XQueryProcessor <filePath> -B/-L");
        }
        //String inputPath = "./testFiles/test21.txt";
        InputStream inputStream = new FileInputStream(inputPath);
        // optimization
        InputStream inputStreamOPT = XQueryOptimizer.optimize(inputStream, joinBias, outputXQueryPath);
        // execute XQuery
        ANTLRInputStream input = new ANTLRInputStream(inputStreamOPT);
        XQUERYLexer xqueryLexer = new XQUERYLexer(input);

        CommonTokenStream commonTokenStream = new CommonTokenStream(xqueryLexer);
        XQUERYParser xqueryParser = new XQUERYParser(commonTokenStream);
        xqueryLexer.removeErrorListeners();
        ParseTree parseTree = xqueryParser.xq();

        MyXqueryVisitor myXqueryVisitor = new MyXqueryVisitor();
        LinkedList<Node> result = myXqueryVisitor.visit(parseTree);

        // saving result to XML file
        if (result.isEmpty()) {
            System.out.println("Empty result!");
        } else {
            LinkedList<Node> finalResult = null;
            Document outputDoc = null;

            File outputFile = new File(outputResultPath);
            if (!outputFile.exists()) {	//文件不存在则创建文件，先创建目录
                File dir = new File(outputFile.getParent());
                dir.mkdirs();
                outputFile.createNewFile();
            }

            try {
                DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
                DocumentBuilder docB = docBF.newDocumentBuilder();
                outputDoc = docB.newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            if (result.size() == 1) {
                System.out.println(result.get(0).getChildNodes().getLength());
                finalResult = result;
            }
            else{
                System.out.println("result size: " + result.size());
                finalResult = makeElem(outputDoc, "result", result);
            }
            writeToFile(outputDoc, finalResult, outputResultPath);
        }
//        if (result == null) { throw new Exception("Invalid XPath Expression!"); }
//        System.out.println("result size: " + result.size());
//        for (Node node : result) {
//            if (node != null) printNode(node, "");
//            System.out.println();
//        }
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

    private static LinkedList<Node> makeElem(Document doc, String tag, LinkedList<Node> list){
        Node result = doc.createElement(tag);
        for (Node node : list) {
            if (node != null) {
                Node newNode = doc.importNode(node, true);
                result.appendChild(newNode);
            }
        }
        LinkedList<Node> results = new LinkedList<>();
        results.add(result);
        return results;
    }

    public static void writeToFile(Document doc, LinkedList<Node> result, String filePath) {
        doc.appendChild(doc.importNode(result.get(0), true));
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(filePath);
            transformer.transform(source, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
