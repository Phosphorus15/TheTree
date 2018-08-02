package net.steepout.ttree;

import net.steepout.ttree.data.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TreeRootTest {

    @Test
    void testToString() {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList");
        node.setValue(Arrays.asList(new StringNode("hello ?"), new DoubleNode(Math.PI)));
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
        ListNode node = new ListNode(null);
        node.setValue(Arrays.asList(new StringNode("hello ?"), new DoubleNode(Math.PI)));
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