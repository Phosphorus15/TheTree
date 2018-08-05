package net.steepout.ttree;

import net.steepout.ttree.utils.BeautifiedPrinter;

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

    public String showValue() {
        return (value == null) ? "null" : value.toString();
    }

    @Override
    public String toString() {
        String result = "";
        if (getName() != null) result += BeautifiedPrinter.quotedString(getName()) + ": ";
        return result + showValue();
    }

}
