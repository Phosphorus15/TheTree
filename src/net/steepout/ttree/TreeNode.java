package net.steepout.ttree;

import java.util.List;

public abstract class TreeNode {

    public abstract String getName();

    public abstract Object getValue();

    public abstract NodeType getType();

    public abstract List<? extends TreeNode> subNodes();

    public int size() {
        return subNodes().size();
    }

    public int asInt() {
        return (int) getValue();
    }

    public long asLong() {
        return (long) getValue();
    }

    public byte asByte() {
        return (byte) getValue();
    }

    public String asString() {
        return (String) getValue();
    }

}
