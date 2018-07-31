package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;

import java.io.Reader;
import java.nio.ByteBuffer;

public class ArbreProcessor extends TreeProcessor {

    static {
        TreeManager.registerProcessor(new ArbreProcessor());
        TreeManager.registerAliases("L'Arbre", "LeArbre", "lar", "LArbre", "TheTree", "the tree", "arbre");
    }

    private ArbreProcessor() {
        super("L'Arbre");
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
