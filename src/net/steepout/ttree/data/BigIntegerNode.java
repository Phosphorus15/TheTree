package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

import java.math.BigInteger;

public class BigIntegerNode extends DataNode<BigInteger> {
    public BigIntegerNode(String caption, BigInteger value) {
        super(caption, value);
    }

    public BigIntegerNode(BigInteger value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_BIG_INTEGER;
    }
}
