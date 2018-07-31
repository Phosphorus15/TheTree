package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class ShortNode extends DataNode<Short> {
    public ShortNode(String caption, Short value) {
        super(caption, value);
    }

    public ShortNode(Short value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_INT16;
    }
}
