package net.steepout.ttree.parser.xml;

import net.steepout.ttree.*;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.utils.BeautifiedPrinter;
import net.steepout.ttree.utils.ParserUtils;

import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
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
        try {
            return ByteBuffer.wrap(toXML(root, 0, false).getBytes(StandardCharsets.UTF_8));
        } catch (InvalidObjectException e) {
            throw new RuntimeException(e);
        }
    }

    public String toXML(TreeNode node, int level, boolean listMode) throws InvalidObjectException {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) tabs.append('\t');
        if (!listMode && node.getName() == null && node.getType().isDataType()
                && node.getType() != NodeType.TYPE_ANNOTATIONS)
            return node.getValue().toString() + "\n";
        String name = listMode ? node.getType().name().toLowerCase() : safeName(node.getName());
        if (node.getValue() == null && node.getType() != NodeType.TYPE_HEADER) {
            return tabs.append("<").append(safeName(node.getName())).append("/>").toString();
        } else if (node.getType() == NodeType.TYPE_DATA_LIST) {
            StringBuilder builder = new StringBuilder(tabs).append("<list id=").append(BeautifiedPrinter.quotedString(name)).append('>').append('\n');
            for (TreeNode n : node.subNodes()) {
                builder.append(toXML(n, level + 1, true)).append('\n');
            }
            builder.append(tabs).append("</list>\n");
            return builder.toString();
        } else if (node.getType() == NodeType.TYPE_HEADER) {
            StringBuilder header = new StringBuilder(tabs).append('<').append(name).append('>').append('\n');
            StringBuilder builder = new StringBuilder();
            for (TreeNode n : node.subNodes()) {
                if (n.getName() != null && n.getName().equals("attributes") && n.getType() == NodeType.TYPE_HEADER
                        && n.subNodes().stream().filter(Objects::nonNull)
                        .map(TreeNode::getType).allMatch(NodeType::isDataType)) {
                    header = new StringBuilder(tabs).append('<').append(name).append(' ');
                    for (TreeNode attr : n.subNodes()) {
                        header.append(attr.getName()).append('=')
                                .append(ParserUtils.forceQuotedValue((DataNode<?>) attr)).append(' ');
                    }
                    header.append('>').append('\n');
                } else builder.append(toXML(n, level + 1, false)).append('\n');
            }
            builder.append(tabs).append("</").append(name).append(">").append('\n');
            return header.append(builder).toString();
        } else if (node.getType().isDataType()) {
            switch (node.getType()) {
                case TYPE_STRING:
                    return tabs.append("<").append(name).append("> ")
                            .append(node.getValue() == null ? "null" : node.getValue().toString()).append(" </")
                            .append(name).append(">").toString();
                case TYPE_ANNOTATIONS:
                    return tabs.append("<!--")
                            .append(node.getValue() == null ? "null" : node.getValue().toString()).append("-->").toString();
                default:
                    return tabs.append("<").append(name).append("> ")
                            .append(((DataNode) node).showValue()).append(" </")
                            .append(name).append(">").toString();
            }
        } else raiseInvalid();
        return "";
    }

    private String safeName(String name) {
        return name == null ? UUID.randomUUID().toString() : name;
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return null;
    }

    @Override
    public boolean isFullyCompatible() {
        return false;
    }
}
