package net.steepout.ttree;

import net.steepout.ttree.utils.BeautifiedPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private String strictProceed(TreeNode node, boolean strict, boolean forceId, boolean forceList, int level) {
        //System.out.println(getName() + " " + strict);
        //Arrays.stream(Thread.currentThread().getStackTrace()).filter(stackTraceElement -> stackTraceElement.getClassName().contains("net"))
        //        .forEach(System.out::println);
        String string = !(node.getType() == NodeType.TYPE_DATA_LIST || node.getType() == NodeType.TYPE_HEADER)
                ? node.toString()
                : ((TreeRoot) node).toString(level + 1, strict);
        //System.out.println(node.getType() + " " + strict + " " + forceId + " " + forceList);
        if (!strict) return string;
        if (forceList) {
            if (node.getName() != null)
                return string.substring(string.indexOf(':') + 1);
        } else if (forceId) {
            //System.out.println("fid " + node.getType());
            if (node.getName() == null)
                return BeautifiedPrinter.quotedString(UUID.randomUUID().toString()) + ": " + string;
        }
        return string;
    }

    public String toString(int level, boolean strictJson) {
        //System.out.println(getName() + " " + strictJson);
        StringBuilder result = new StringBuilder();
        if (name != null && !(level == 1 && strictJson))
            result = new StringBuilder(BeautifiedPrinter.quotedString(name) + ": ");
        boolean forceId = (strictJson && getType() != NodeType.TYPE_DATA_LIST);
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append('\t');
        if (getType() == NodeType.TYPE_DATA_LIST) result.append("[\n");
        else result.append("{\n");
        if (subNodes.stream()
                .allMatch(node -> node.getType().isDataType())) {
            result.deleteCharAt(result.length() - 1);
            tabs = new StringBuilder();
            for (TreeNode node : subNodes) {
                result.append(strictProceed(node, strictJson, forceId
                        , getType() == NodeType.TYPE_DATA_LIST, level)).append(", ");
            }
        } else
            for (TreeNode node : subNodes) {
                result.append(tabs)
                        .append((strictProceed(node, strictJson, forceId
                                , getType() == NodeType.TYPE_DATA_LIST, level))).append(", \n");
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
        return toString(1, false);
    }

}
