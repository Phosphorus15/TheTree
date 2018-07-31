package net.steepout.ttree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeManagerTest {

    @BeforeAll
    static void setUp() {
        try {
            Class.forName("net.steepout.ttree.parser.json.JsonProcessor");
            Class.forName("net.steepout.ttree.parser.l_arbre.ArbreProcessor");
            Class.forName("net.steepout.ttree.parser.xml.XMLProcessor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerAliases() {
        TreeManager.registerAliases("json", "gson?");
        assertEquals("json", Objects.requireNonNull(TreeManager.findParser("gson?")).getName());
    }

    @Test
    void findSerializer() {
        assertEquals("L'Arbre", Objects.requireNonNull(TreeManager.findParser("thetree")).getName());
    }

    @Test
    void findParser() {
        assertEquals("xml", Objects.requireNonNull(TreeManager.findParser("html")).getName());
    }

    @Test
    void availableProcessors() {
        assertEquals(3, TreeManager.availableProcessors().size());
    }
}