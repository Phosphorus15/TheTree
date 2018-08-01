package net.steepout.ttree;

import net.steepout.ttree.data.DoubleNode;
import net.steepout.ttree.data.IdentifierNode;
import net.steepout.ttree.data.IntNode;
import net.steepout.ttree.data.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TreeRootTest {

    @Test
    void testToString() {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList", NodeType.TYPE_INT32);
        node.setValue(Arrays.asList(1, 3, 6, 7));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot("sub0");
        TreeRoot sub2 = new TreeRoot("sub");
        sub1.subNodes().add(new DoubleNode("pi", Math.PI));
        sub1.subNodes().add(new IntNode("vl", 233));
        sub2.subNodes().add(new IdentifierNode("identifier", "id_test"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        System.out.println(root.toString());
    }

    @Test
    void testEmptyToString() {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode(null, NodeType.TYPE_INT32);
        node.setValue(Arrays.asList(1, 3, 6, 7));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot("sub0");
        TreeRoot sub2 = new TreeRoot(null);
        sub1.subNodes().add(new DoubleNode("pi", Math.PI));
        sub1.subNodes().add(new IntNode("vl", 233));
        sub2.subNodes().add(new IdentifierNode("identifier", "id_test"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        System.out.println(root.toString());
    }
}