package net.steepout.ttree;

import java.util.LinkedList;
import java.util.List;

public class TreeRoot extends EditableNode {

    private String name;

    private List<EditableNode> subNodes;

    public TreeRoot(String caption) {
        setName(caption);
        subNodes = new LinkedList<EditableNode>();
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
}
