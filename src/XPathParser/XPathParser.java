import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Node;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

public class XPathParser {
    public static void main(String[] args) throws Exception {
        String filename = "/Users/mac126/19fall/udemy/spring/cse232bProject/testFiles/test1.txt";
        InputStream in = new FileInputStream(filename);
        ANTLRInputStream input = new ANTLRInputStream(in);
        XPathParser.XPATHgen.XPATHLexer xPathLexer = new XPathParser.XPATHgen.XPATHLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(xPathLexer);
        XPathParser.XPATHgen.XPATHParser xpathParser = new XPathParser.XPATHgen.XPATHParser(commonTokenStream);
        xpathParser.removeErrorListeners();
        ParseTree parseTree = xpathParser.ap();

        CustomerVisitor customerVisitor = new CustomerVisitor();
        LinkedList<Node> result = customerVisitor.visit(parseTree);
        System.out.println(result.toString());

    }
}
