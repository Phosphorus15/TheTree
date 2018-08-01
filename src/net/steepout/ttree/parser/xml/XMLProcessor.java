package net.steepout.ttree.parser.xml;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.InputStream;
import java.nio.ByteBuffer;

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
        return null;
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return null;
    }
}
