package net.steepout.ttree;

import net.steepout.ttree.utils.BeautifiedPrinter;

import java.util.LinkedList;
import java.util.List;

public class TreeRoot extends EditableNode {

    private String name;

    private List<EditableNode> subNodes;

    public TreeRoot(String caption) {
        setName(caption);
        subNodes = new LinkedList<EditableNode>();
    }

    public TreeRoot() {
        this(null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_HEADER;
    }

    @Override
    public List<EditableNode> subNodes() {
        return subNodes;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setValue(Object object) {

    }

    public String toString(int level) {
        StringBuilder result = new StringBuilder();
        if (name != null) result = new StringBuilder(BeautifiedPrinter.quotedString(name) + ": ");
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append('\t');
        result.append("{\n");
        for (TreeNode node : subNodes) {
            result.append(tabs).append((!(node.getType().isDataType() || node.getType().equals(NodeType.TYPE_DATA_LIST)))
                    ? ((TreeRoot) node).toString(level + 1)
                    : node.toString()).append("\n");
        }
        if (tabs.length() != 0) tabs = tabs.deleteCharAt(tabs.length() - 1);
        result.append(tabs).append("}");
        return result.toString();
    }

    @Override
    public String toString() {
        return toString(1);
    }

}
