package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class BlobNode extends DataNode<byte[]> {

    public BlobNode(String caption, byte[] value) {
        super(caption, value);
    }

    public BlobNode(byte[] value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_BLOB;
    }
}
