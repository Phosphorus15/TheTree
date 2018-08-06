package net.steepout.ttree.parser.yaml;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.AnnotationsNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

class YamlProcessorTest {

    static final String jsonString =
            "{'id' : 233,'floating': 1.7, 'string': \"might be a \\nstring\"" +
                    ", 'tree': {\"deeper\": {},'complex' : [2,3,5,7,'233'],'value' : 23333333333333333}" +
                    "}";

    static TreeRoot root;

    static {
        try {
            Class.forName("net.steepout.ttree.parser.json.JsonProcessor");
            Class.forName("net.steepout.ttree.parser.yaml.YamlProcessor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            root = TreeManager.findParser("json").parse(new ByteArrayInputStream(jsonString.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.subNodes().add(new AnnotationsNode("Test annotation"));
        root.subNodes().get(3).subNodes().get(0).subNodes().add(new AnnotationsNode("Embedded"));
    }

    @Test
    void serialize() {
        ByteBuffer buffer = TreeManager.findSerializer("yaml").serialize(root);
        String str = new String(buffer.array());
        System.out.println(str);
    }
}
