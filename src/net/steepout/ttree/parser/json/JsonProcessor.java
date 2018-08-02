package net.steepout.ttree.parser.json;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class JsonProcessor extends TreeProcessor {

    static {
        TreeManager.registerProcessor(new JsonProcessor());
        TreeManager.registerAliases("json", "jsObjectNotation");
    }

    private JsonProcessor() {
        super("json");
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        return ByteBuffer.wrap(root.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return null;
    }
}
