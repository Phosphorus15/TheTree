package net.steepout.ttree.parser;

import java.io.InvalidObjectException;

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

    protected void raiseInvalid() throws InvalidObjectException {
        throw new InvalidObjectException("Invalid " + name + " object !");
    }

    protected void raiseInvalid(String tips) throws InvalidObjectException {
        throw new InvalidObjectException("Invalid " + name + " object (" + tips + ")");
    }
}
