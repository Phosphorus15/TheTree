package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class LongNode extends DataNode<Long> {
    public LongNode(String caption, Long value) {
        super(caption, value);
    }

    public LongNode(Long value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_INT64;
    }
}
