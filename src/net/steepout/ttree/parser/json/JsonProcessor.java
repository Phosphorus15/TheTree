package net.steepout.ttree.parser.json;

import net.steepout.ttree.EditableNode;
import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.utils.ParserUtils;

import java.io.*;
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
    public TreeRoot parse(InputStream stream) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        JsonRootNode root = new JsonRootNode();
        int ch = ParserUtils.readUntilValid(reader);
        if (ch != '{') raiseInvalid();
        parseJson(root, reader);
        return root;
    }

    public void parseJson(EditableNode node, Reader reader) throws IOException {
        String key = "";
        String raw = "";
        boolean fullFilled = false;
        while (true) {
            int ch = ParserUtils.readUntilValid(reader);
            if (!key.isEmpty()) {
                if (ch == ':') {
                    fullFilled = true;
                } else if (fullFilled) {
                    ch = ParserUtils.readUntilValid(reader);
                    if (ch == '{') {
                        TreeRoot root = new TreeRoot(key);
                        node.subNodes().add(root);
                        parseJson(root, reader);
                    } else if (ch == '[') {

                    } else if (ch == '\'' || ch == '"') {

                    } else {
                        raw = ch + ParserUtils.readUntilEliminate(reader); // mark reset
                    }
                } else raiseInvalid();
            } else if (ch == '\'' || ch == '"') {
                key = ParserUtils.readEffectiveString(reader, ch);
                fullFilled = true;
            } else raiseInvalid();
        }
    }
}
