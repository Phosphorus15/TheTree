package net.steepout.ttree.data;

import net.steepout.ttree.EditableNode;
import net.steepout.ttree.NodeType;
import net.steepout.ttree.TreeRoot;

import java.util.Collection;
import java.util.List;


/**
 * List node is an partially unstable node type, because it would behaved like a root object
 * <p>
 * While it doesn't actually is. it performed the same as tree root when using for non-storing purpose
 * <p>
 * (except the changes '{}' -> '[]' when using toString())
 * <p>
 * However, if you try to store them using a serializer, <b>all sub-nodes</b> of the list node would be got rid of
 * <p>
 * their "name" property, which is just like a list, and putting an unidentified object into it might cause unexpected problem
 */
public class ListNode extends TreeRoot {

    public ListNode(String caption) {
        super(caption);
    }

    public ListNode() {
        this(null);
    }

    @Override
    public List<EditableNode> getValue() {
        return subNodes();
    }

    @Override
    public void setValue(Object object) {
        if (object instanceof List) {
            subNodes.clear();
            subNodes.addAll((Collection<? extends EditableNode>) object);
        }
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_DATA_LIST;
    }

}
