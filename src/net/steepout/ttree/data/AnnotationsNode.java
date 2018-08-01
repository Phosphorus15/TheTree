package net.steepout.ttree.data;

import net.steepout.ttree.NodeType;

public class AnnotationsNode extends StringNode {
    public AnnotationsNode(String caption, String value) {
        super(caption, value);
    }

    public AnnotationsNode(String value) {
        super(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_ANNOTATIONS;
    }
}
