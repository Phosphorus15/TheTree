package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class ByteNode extends DataNode<Byte> {
    public ByteNode(String caption, Byte value) {
        super(caption, value);
    }

    public ByteNode(Byte value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_BYTE;
    }
}
