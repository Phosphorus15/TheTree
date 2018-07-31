package net.steepout.ttree.data;

import net.steepout.ttree.DataNode;
import net.steepout.ttree.NodeType;

import java.util.ArrayList;
import java.util.List;

public class ListNode extends DataNode<List<?>> {

    public ListNode(String caption, NodeType listType) {
        super(caption, null);
        setListType(listType);
        value = new ArrayList<>();
    }

    public ListNode(NodeType listType) {
        this(null, listType);
    }

    public NodeType getListType() {
        return listType;
    }

    public void setListType(NodeType listType) {
        this.listType = listType;
    }

    NodeType listType;

    @Override
    public NodeType getType() {
        return NodeType.TYPE_DATA_LIST;
    }

}
