package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.io.IOException;
import java.io.InputStream;

public interface TreeParser {

    String getName();

    TreeSerializer getSerializer();

    TreeRoot parse(InputStream stream) throws IOException;

}
