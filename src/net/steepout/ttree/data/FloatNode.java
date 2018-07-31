package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class FloatNode extends DataNode<Float> {

    public FloatNode(String caption, Float value) {
        super(caption, value);
    }

    public FloatNode(Float value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_FLOAT;
    }

}
