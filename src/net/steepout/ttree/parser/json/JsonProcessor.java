package net.steepout.ttree.parser.json;

import net.steepout.ttree.EditableNode;
import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.data.BlobNode;
import net.steepout.ttree.data.ListNode;
import net.steepout.ttree.data.StringNode;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.utils.ParserUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * A typical JSON parser & serializer (with no annotations' or identifiers' support)
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
        parseJson(root, reader, false);
        return root;
    }

    public void parseJson(EditableNode node, Reader reader, boolean listMode) throws IOException {
        //System.out.println(node);
        String key = (listMode) ? null : "";
        String raw;
        boolean fullFilled = listMode, paired = false;
        while (true) {
            fullFilled |= listMode;
            int ch = ParserUtils.readUntilValid(reader);
            if (ch == '}' && !listMode) break;
            if (ch == ']' && listMode) break;
            if (fullFilled && paired) {
                if (ch == ',') {
                    fullFilled = paired = false;
                    key = (listMode) ? null : "";
                    continue;
                } else raiseInvalid("unexpected symbol " + ((char) ch));
            }
            if (key == null || !key.isEmpty()) {
                if (ch == ':') {
                    fullFilled = true;
                } else if (fullFilled) {
                    if (ch == '{') {
                        TreeRoot root = new TreeRoot(key);
                        node.subNodes().add(root);
                        parseJson(root, reader, false);
                    } else if (ch == '[') {
                        ListNode list = new ListNode(key);
                        node.subNodes().add(list);
                        parseJson(list, reader, true);
                    } else if (ch == '\'' || ch == '"') {
                        raw = ParserUtils.readEffectiveString(reader, ch);
                        EditableNode n;
                        if (raw.length() >= 5 && raw.startsWith("Blob-")) {
                            raw = raw.substring(5);
                            n = new BlobNode(key, Base64.getDecoder().decode(raw));
                        } else {
                            n = new StringNode(key, raw);
                        }
                        node.subNodes().add(n);
                    } else {
                        //  System.out.println("redundant : " + ((char) ch));
                        raw = ((char) ch) + ParserUtils.readUntilEliminate(reader); // mark reset
                        // System.out.println(raw);
                        EditableNode n = ParserUtils.resolveRawType(key, raw);
                        if (n == null) raiseInvalid("unidentified object type : " + raw);
                        node.subNodes().add(n);
                    }
                    paired = true;
                } else raiseInvalid();
            } else if (ch == '\'' || ch == '"') {
                //System.out.println("id read");
                key = ParserUtils.readEffectiveString(reader, ch);
                //System.out.println(key);
            } else raiseInvalid();
        }
    }

    public void parseList(ListNode node, Reader reader) {

    }
}
