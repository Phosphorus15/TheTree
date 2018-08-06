package net.steepout.ttree.parser.xml;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.AnnotationsNode;
import net.steepout.ttree.data.IntNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

//import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLProcessorTest {

    static final String jsonString =
            "{'id' : 233,'floating': 1.7, 'string': \"might be a \\nstring\"" +
                    ", 'tree': {\"deeper\": {},'complex' : [2,3,5,7,'233', [1,7,9,2.0, {'uid': null}]],'value' : 23333333333333333}" +
                    "}";

    static TreeRoot root;

    static {
        try {
            Class.forName("net.steepout.ttree.parser.json.JsonProcessor");
            Class.forName("net.steepout.ttree.parser.xml.XMLProcessor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            root = TreeManager.findParser("json").parse(new ByteArrayInputStream(jsonString.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.subNodes().add(new AnnotationsNode("Test Annotation"));
        TreeRoot headers = new TreeRoot("attributes");
        root.subNodes().add(headers);
        headers.subNodes().add(new IntNode("version", TreeManager.VERSION));
    }

    @Test
    void serialize() {
        ByteBuffer buffer = TreeManager.findSerializer("xml").serialize(root);
        String str = new String(buffer.array());
        System.out.println(str);
    }

    @Test
    void parse() {
    }
}