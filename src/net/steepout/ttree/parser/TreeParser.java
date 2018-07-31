package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.io.InputStream;
import java.io.Reader;

public interface TreeParser {

    String getName();

    TreeSerializer getSerializer();

    TreeRoot parse(Reader reader);

    TreeRoot parse(InputStream stream);

}
