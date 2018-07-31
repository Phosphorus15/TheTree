package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class TreeProcessor implements TreeParser, TreeSerializer {

    private String name;

    public TreeProcessor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TreeParser getParser() {
        return this;
    }

    @Override
    public TreeSerializer getSerializer() {
        return this;
    }

    @Override
    public TreeRoot parse(InputStream stream) {
        return parse(new InputStreamReader(stream));
    }
}
