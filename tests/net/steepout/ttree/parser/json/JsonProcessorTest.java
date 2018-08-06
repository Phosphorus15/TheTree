package net.steepout.ttree.parser.json;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.parser.TreeParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonProcessorTest {

    static final String jsonString =
            "{'id' : 233,'floating': 1.7, 'string': \"might be a \\nstring\"" +
                    ", 'tree': {\"deeper\": {},'list' : [2,3,5,7,'233'],'value' : 23333333333333333}" +
                    "}";

    static final String matrixJson = "{'matrix':[[{'id' : 1},{'id' : 2},{}],[{},{\"value\" : 2.7},{}],[{},{},{}]]}";

    @BeforeAll
    public static void setUp() throws ClassNotFoundException {
        Class.forName("net.steepout.ttree.parser.json.JsonProcessor");
    }

    @Test
    public void testParse() throws IOException {
        TreeParser parser = TreeManager.findParser("json");
        assertNotNull(parser);
        JsonRootNode node = (JsonRootNode) parser.parse(new ByteArrayInputStream(jsonString.getBytes()));
        assertTrue(node.toString().contains("\"list\": [2, 3, 5, 7, \"233\" ]"));
    }

    @Test
    public void testMatrix() throws IOException {
        TreeParser parser = TreeManager.findParser("json");
        assertNotNull(parser);
        JsonRootNode node = (JsonRootNode) parser.parse(new ByteArrayInputStream(matrixJson.getBytes()));
        assertEquals(147, node.toString().length());
    }

}
