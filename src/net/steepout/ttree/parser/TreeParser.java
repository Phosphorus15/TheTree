package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.io.IOException;
import java.io.InputStream;

public interface TreeParser {

    String getName();

    TreeSerializer getSerializer();

    TreeRoot parse(InputStream stream) throws IOException;

    /**
     * To determine, if ever this parser/serializer set can fully supports the conversion from other tree source.
     * and can also be converted back without losing information.
     *
     * @return if the parser/serializer is fully compatible
     */
    boolean isFullyCompatible();

}
