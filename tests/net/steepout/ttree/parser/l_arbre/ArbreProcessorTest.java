package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.*;
import net.steepout.ttree.utils.BeautifiedPrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ArbreProcessorTest {

    @BeforeAll
    static void setUp() throws ClassNotFoundException {
        Class.forName("net.steepout.ttree.parser.l_arbre.ArbreProcessor");
    }

    @Test
    void serialize() {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList");
        node.setValue(Arrays.asList(new StringNode("hello ?"), new DoubleNode(Math.PI)));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot(null);
        TreeRoot sub2 = new TreeRoot("sub");
        sub1.subNodes().add(new DoubleNode("pi", Math.PI));
        sub1.subNodes().add(new IntNode("vl", 233));
        sub2.subNodes().add(new IdentifierNode("idt"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        ((ArbreProcessor) TreeManager.defaultParser()).setCompress(true);
        byte[] compressed = TreeManager.defaultSerializer().serialize(root).array();
        assertEquals(138, compressed.length);
        BeautifiedPrinter.printBytes(compressed);
    }

    @Test
    void parse() throws IOException {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList");
        node.setValue(Arrays.asList(new StringNode("hello ?"), new DoubleNode(Math.PI)));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot(null);
        TreeRoot sub2 = new TreeRoot("sub");
        sub1.subNodes().add(new DoubleNode("pi", Math.PI));
        sub1.subNodes().add(new IntNode("vl", 233));
        sub2.subNodes().add(new IdentifierNode("idt"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        LARAttributiveRoot larRoot = (LARAttributiveRoot) TreeManager.defaultParser()
                .parse(new ByteArrayInputStream(TreeManager.defaultSerializer().serialize(root).array()));
    }

}
