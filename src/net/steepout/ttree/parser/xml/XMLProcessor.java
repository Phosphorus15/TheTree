package net.steepout.ttree.parser.xml;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.Reader;
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
    public TreeRoot parse(Reader reader) {
        return null;
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        return null;
    }
}
