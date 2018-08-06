package net.steepout.ttree.parser;

import net.steepout.ttree.TreeRoot;

import java.nio.ByteBuffer;

public interface TreeSerializer {

    String getName();

    TreeParser getParser();

    ByteBuffer serialize(TreeRoot root);

    /**
     * To determine, if ever this parser/serializer set can fully supports the conversion from other tree source.
     * and can also be converted back without losing information.
     *
     * @return if the parser/serializer is fully compatible
     */
    boolean isFullyCompatible();

}
