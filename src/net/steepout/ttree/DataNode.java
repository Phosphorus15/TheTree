package net.steepout.ttree;

import java.util.ArrayList;
import java.util.List;

public abstract class DataNode<T> extends TreeRoot {

    public DataNode(String caption, T value) {
        super(caption);
        setValue(value);
    }

    public DataNode(T value) {
        this(null, value);
    }

    public T value;

    @Override
    public List<EditableNode> subNodes() {
        return new ArrayList<>();
    }

    @Override
    public void setValue(Object object) {
        value = (T) object;
    }

    @Override
    public T getValue() {
        return value;
    }
}
