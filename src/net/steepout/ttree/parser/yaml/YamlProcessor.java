package net.steepout.ttree.parser.yaml;

import net.steepout.ttree.*;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Yaml is a commonly used 'non markup language' (as is claimed by itself)
 * <p>
 * While we still regard it as a markup language :)
 * <p>
 * It should be known that the top root of a yaml tree should not preserve any name(identifier) data
 * <p>
 * Otherwise, it would be erased
 */
public class YamlProcessor extends TreeProcessor {

    static {
        TreeManager.registerProcessor(new YamlProcessor());
        TreeManager.registerAliases("yaml", "yml");
    }

    private YamlProcessor() {
        super("yaml");
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return null;
    }

    @Override
    public boolean isFullyCompatible() {
        return false;
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        try {
            return ByteBuffer.wrap(toYaml(root, 0, false).getBytes(StandardCharsets.UTF_8));
        } catch (InvalidObjectException e) {
            throw new RuntimeException(e);
        }
    }

    public String toYaml(TreeNode node, int level, boolean listMode) throws InvalidObjectException {
        StringBuilder builder = new StringBuilder();
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append(' ');
        for (TreeNode n : node.subNodes()) {
            if (n.getType() == NodeType.TYPE_HEADER) {
                if (listMode) raiseInvalid("unexpected type " + n.getType() + " in " + node.getType());
                builder.append(tabs.toString()).append(safeName(n.getName())).append(":\n");
                builder.append(toYaml(n, level + 1, false));
            } else if (n.getType().isDataType()) { // TODO comments' support
                if (listMode)
                    builder.append(tabs.toString()).append("- ").append(((DataNode) n).showValue()).append("\n");
                else
                    builder.append(tabs.toString()).append(safeName(n.getName())).append(" : ").append(((DataNode) n).showValue()).append("\n");
            } else if (n.getType() == NodeType.TYPE_DATA_LIST) {
                if (listMode) raiseInvalid("unexpected type " + n.getType() + " in " + node.getType());
                builder.append(tabs.toString()).append(safeName(n.getName())).append(":\n");
                builder.append(toYaml(n, level + 1, true));
            } else raiseInvalid("unidentified node type");
        }
        return builder.toString();
    }

    private String safeName(String name) {
        return name == null ? UUID.randomUUID().toString() : name;
    }
}
