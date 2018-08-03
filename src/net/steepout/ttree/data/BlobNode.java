package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;
import net.steepout.ttree.utils.BeautifiedPrinter;

import java.util.Base64;

public class BlobNode extends DataNode<byte[]> {

    public BlobNode(String caption, byte[] value) {
        super(caption, value);
    }

    public BlobNode(byte[] value) {
        super(value);
    }

    @Override
    public String showValue() {
        return BeautifiedPrinter.quotedString("Blob-" + Base64.getEncoder().encodeToString(value));
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_BLOB;
    }
}
