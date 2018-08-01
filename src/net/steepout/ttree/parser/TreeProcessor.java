package net.steepout.ttree.parser;

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

}
