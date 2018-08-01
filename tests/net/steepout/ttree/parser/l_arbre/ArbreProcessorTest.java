package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.NodeType;
import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.DoubleNode;
import net.steepout.ttree.data.IdentifierNode;
import net.steepout.ttree.data.IntNode;
import net.steepout.ttree.data.ListNode;
import net.steepout.ttree.utils.BeautifiedPrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ArbreProcessorTest {

    @BeforeAll
    static void setUp() throws ClassNotFoundException {
        Class.forName("net.steepout.ttree.parser.l_arbre.ArbreProcessor");
    }

    @Test
    void serialize() {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList", NodeType.TYPE_INT32);
        node.setValue(Arrays.asList(1, 3, 6, 7));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot(null);
        TreeRoot sub2 = new TreeRoot("sub");
        sub1.subNodes().add(new DoubleNode("pi", Math.PI));
        sub1.subNodes().add(new IntNode("vl", 233));
        sub2.subNodes().add(new IdentifierNode("idt"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        BeautifiedPrinter.printBytes(TreeManager.defaultSerializer().serialize(root).array());
    }

}
