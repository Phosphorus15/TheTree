package net.steepout.ttree;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EditableNode extends TreeNode {

    @Override
    abstract List<EditableNode> subNodes();

    abstract void setName(String name);

    abstract void setValue(Object object);

    public TreeNode asUneditable() {
        return new ProtectedNode(this);
    }

    private class ProtectedNode extends TreeNode {

        TreeNode node;

        public ProtectedNode(TreeNode editableNode) {
            this.node = editableNode;
        }

        @Override
        String getName() {
            return node.getName();
        }

        @Override
        Object getValue() {
            return node.getValue();
        }

        @Override
        NodeType getType() {
            return node.getType();
        }

        public TreeNode asProtectedNode(TreeNode node) {
            return new ProtectedNode(node);
        }

        @Override
        List<TreeNode> subNodes() {
            return Collections.unmodifiableList(node.subNodes().stream()
                    .map(this::asProtectedNode).collect(Collectors.toList()));
        }

    }

}