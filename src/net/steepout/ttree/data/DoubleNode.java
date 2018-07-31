package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class DoubleNode extends DataNode<Double> {
    public DoubleNode(String caption, Double value) {
        super(caption, value);
    }

    public DoubleNode(Double value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_DOUBLE_FLOAT;
    }
}
