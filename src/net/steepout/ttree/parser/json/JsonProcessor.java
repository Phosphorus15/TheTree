package net.steepout.ttree.parser.json;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.Reader;
import java.nio.ByteBuffer;

public class JsonProcessor extends TreeProcessor {

    static {
        TreeManager.registerProcessor(new JsonProcessor());
        TreeManager.registerAliases("json", "jsObjectNotation");
    }

    private JsonProcessor() {
        super("json");
    }

    @Override
    public TreeRoot parse(Reader reader) {
        return null;
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        return null;
    }
}
