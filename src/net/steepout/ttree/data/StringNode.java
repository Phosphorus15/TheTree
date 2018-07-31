package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

public class StringNode extends DataNode<String> {

    public StringNode(String caption, String value) {
        super(caption, value);
    }

    public StringNode(String value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_STRING;
    }
}
