package net.steepout.ttree.parser.json;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A typical JSON parser & serializer
 * <p>
 * it acts as any other json utils do, as you could see, any value would be parsed into DataNode or its subclass
 * <p>
 * note that if a node is without it's name (or, let's say "identifier"), and is not the top node or not in a list,
 * <p>
 * the serializer, in the order of being compatible to other json parser, would <b>generate an random</b> UUID as its name.
 * <p>
 * What's more, it should be known that nodes like "BLOB" (which would become a BASE64 string), or "BigDecimal" are not
 * that compatible with other json parser and would cause <b>unexpected problems</b>
 */
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
        return ByteBuffer.wrap(root.toString(1, true).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        JSONRootNode root = new JSONRootNode();
        return root;
    }
}
