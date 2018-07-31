package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class IntNode extends DataNode<Integer> {


    public IntNode(String caption, Integer value) {
        super(caption, value);
    }

    public IntNode(Integer value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_INT32;
    }
}
