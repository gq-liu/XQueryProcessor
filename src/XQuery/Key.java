import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Key {
    private ArrayList<Node> nodes;

    public Key() {
        nodes = new ArrayList<>();
    }
    public void addNode(Node node) {
        nodes.add(node);
    }
    public Node getNodeAt(int i) {
        return nodes.get(i);
    }
    public int size() { return nodes.size(); }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key)) { return false; }
        if (obj == this) { return true; }
        Key o = (Key)obj;
        if (o.size() != this.size()) { return false; }
        for (int i = 0; i < this.size(); i++) {
            if (!this.getNodeAt(i).isEqualNode(o.getNodeAt(i))) { return false; }
        }
        return true;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for(Node node : nodes){
            sb.append(node.getFirstChild().getTextContent());
        }
        return sb.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nodes.size(); i++) {
            StringBuilder sb = new StringBuilder();
            result.append(printNode(nodes.get(i), "", sb));
        }
        return result.toString();

    }
    private static String printNode(Node node, String tab, StringBuilder sb) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sb.append(tab + "<" + node.getNodeName().toUpperCase() + ">");
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null && nodeMap.getLength() != 0) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    sb.append(printNode(nodeMap.item(i), tab + "  ", sb));
                }
            }
            NodeList nodeList = node.getChildNodes();
            if (nodeList != null) {
                if (nodeList.getLength() == 1 && nodeList.item(0).getNodeType() == Node.TEXT_NODE) {
                    sb.append(node.getTextContent());
                    sb.append("</" + node.getNodeName().toUpperCase() + ">");
                } else {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        System.out.println();
                        sb.append(printNode(nodeList.item(i), tab + "  ", sb));
                    }
                    sb.append("\n");
                    sb.append(tab + "</" + node.getNodeName().toUpperCase() + ">");
                }
            }


        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            //System.out.println(tab + "<@" + node.getNodeName() + "=" + node.getNodeValue() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            sb.append(tab + node.getTextContent());
        } else if (node.getNodeType() == Node.DOCUMENT_NODE) {
            sb.append("DOCUMENT_NODE");
        }
        return sb.toString();
    }

}
