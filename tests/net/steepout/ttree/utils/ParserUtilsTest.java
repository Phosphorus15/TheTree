package net.steepout.ttree.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserUtilsTest {

    @Test
    void isIndentLetter() {
        for (char ch : Arrays.asList(' ', '\t', '\n', '\r')) {
            assertTrue(ParserUtils.isIndentLetter(ch));
        }
    }

    @Test
    void isBracket() {
    }

    @Test
    void isEliminator() {
    }

    @Test
    void readEffectiveString() {
    }
}