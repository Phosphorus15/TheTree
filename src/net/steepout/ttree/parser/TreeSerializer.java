package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.nio.ByteBuffer;

public interface TreeSerializer {
    
    String getName();

    TreeParser getParser();

    ByteBuffer serialize(TreeRoot root);

}
