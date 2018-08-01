package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

import java.math.BigDecimal;

public class BigDecimalNode extends DataNode<BigDecimal> {
    public BigDecimalNode(String caption, BigDecimal value) {
        super(caption, value);
    }

    public BigDecimalNode(BigDecimal value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_BIG_DECIMAL;
    }
}
