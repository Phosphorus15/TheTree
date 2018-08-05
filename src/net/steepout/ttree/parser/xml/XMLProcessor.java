package net.steepout.ttree.parser.xml;

import net.steepout.ttree.NodeType;
import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeNode;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class XMLProcessor extends TreeProcessor {

    static {
        TreeManager.registerProcessor(new XMLProcessor());
        TreeManager.registerAliases("xml", "HTML", "HTMLX", "HTML5");
    }

    private XMLProcessor() {
        super("xml");
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        return ByteBuffer.wrap(toXML(root, 1).getBytes(StandardCharsets.UTF_8));
    }

    public String toXML(TreeNode node, int level) {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append('\t');
        if (node.getName() == null && node.getType() == NodeType.TYPE_STRING) return node.getValue().toString() + "\n";
        if (node.getValue() == null) {
            return tabs.append("<").append(safeName(node.getName())).append("/>").toString();
        } else if (node.getType() == NodeType.TYPE_DATA_LIST) {

        } else if (node.getType() == NodeType.TYPE_HEADER) {
            //FIXME a lot of damn compatibility-related problems
        } else return tabs.append("<");
    }

    private String safeName(String name) {
        return name == null ? UUID.randomUUID().toString() : name;
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return null;
    }
}
