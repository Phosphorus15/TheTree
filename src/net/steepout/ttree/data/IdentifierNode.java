package net.steepout.ttree.data;

import net.steepout.ttree.NodeType;

public class IdentifierNode extends StringNode {
    public IdentifierNode(String caption, String value) {
        super(caption, value);
    }

    public IdentifierNode(String value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_IDENTIFIER;
    }
}
