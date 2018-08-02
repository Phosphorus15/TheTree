package net.steepout.ttree;

import net.steepout.ttree.utils.BeautifiedPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * The root of a tree (or a tree's section)
 * <p>
 * We strongly recommend the base root node to be with a 'null' name value to be compatible with json etc.
 */
public class TreeRoot extends EditableNode {

    private String name;

    protected List<EditableNode> subNodes;

    public TreeRoot(String caption) {
        setName(caption);
        subNodes = new ArrayList<>();
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

    private String toString(int level) {
        StringBuilder result = new StringBuilder();
        if (name != null) result = new StringBuilder(BeautifiedPrinter.quotedString(name) + ": ");
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append('\t');
        if (getType() == NodeType.TYPE_DATA_LIST) result.append("[\n");
        else result.append("{\n");
        if (subNodes.stream()
                .allMatch(node -> node.getType().isDataType())) {
            result.deleteCharAt(result.length() - 1);
            tabs = new StringBuilder();
            for (TreeNode node : subNodes) {
                result.append(node.toString()).append(", ");
            }
        } else
            for (TreeNode node : subNodes) {
                result.append(tabs)
                        .append((!(node.getType().isDataType()))
                                ? (((TreeRoot) node).toString(level + 1))
                                : node.toString()).append(", \n");
            }
        if (tabs.length() != 0) tabs = tabs.deleteCharAt(tabs.length() - 1);
        if (result.indexOf(",") != -1) {
            int loc = result.lastIndexOf(",");
            result = result.replace(loc, loc + 1, "");
        }
        result.append(tabs);
        if (getType() == NodeType.TYPE_DATA_LIST) result.append("]");
        else result.append("}");
        return result.toString();
    }

    @Override
    public String toString() {
        return toString(1);
    }

}
