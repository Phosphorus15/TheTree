package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        assertEquals(139, compressed.length); // Theoretically
        //BeautifiedPrinter.printBytes(compressed);
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
        LARAttributiveRootNode larRoot = (LARAttributiveRootNode) TreeManager.defaultParser()
                .parse(new ByteArrayInputStream(TreeManager.defaultSerializer().serialize(root).array()));
        assertEquals(root.toString(), larRoot.toString());
        //System.out.println(larRoot.toArrtibutiveString());
    }

    @Test
    void parseBigDecimalNBlob() throws IOException {
        TreeRoot root = new TreeRoot("primary");
        ListNode node = new ListNode("intList");
        node.setValue(Arrays.asList(new StringNode("hello ?"), new BigDecimalNode(BigDecimal.valueOf(Math.PI))));
        root.subNodes().add(node);
        TreeRoot sub1 = new TreeRoot(null);
        TreeRoot sub2 = new TreeRoot("sub");
        sub1.subNodes().add(new DoubleNode("pi", Math.E));
        sub1.subNodes().add(new BigIntegerNode(BigInteger.TEN.pow(200)));
        sub1.subNodes().add(new BlobNode("blob", BigInteger.TEN.pow(200).toByteArray()));
        sub2.subNodes().add(new IdentifierNode("idt"));
        root.subNodes().add(sub1);
        root.subNodes().add(sub2);
        LARAttributiveRootNode larRoot = (LARAttributiveRootNode) TreeManager.defaultParser()
                .parse(new ByteArrayInputStream(TreeManager.defaultSerializer().serialize(root).array()));
        assertEquals(root.toString(), larRoot.toString());
        root.subNodes().add(new TreeRoot());
        root.subNodes().get(0).subNodes().add(new ListNode());
    }

}
