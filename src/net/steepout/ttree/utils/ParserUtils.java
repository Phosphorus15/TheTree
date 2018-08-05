package net.steepout.ttree.utils;

import net.steepout.ttree.EditableNode;
import net.steepout.ttree.data.*;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ParserUtils {

    public static boolean isIndentLetter(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
    }

    /**
     * @param ch the char to be proceed
     * @return whether it is or bracket or not, while [-1, 0, 1] means [left bracket, not bracket, right bracket]
     */
    public static int isBracket(int ch) {
        if (ch == '[' || ch == '(' || ch == '{') return -1;
        else if (ch == ']' || ch == ')' || ch == '}') return 1;
        else return 0;
    }

    public static boolean isStringEliminator(int ch) {
        return ch == '"' || ch == '\'';
    }

    public static boolean isEliminator(int ch) {
        return isBracket(ch) != 0 || isIndentLetter(ch) || isStringEliminator(ch) || ch == ',';
    }

    public static int readUntilValid(Reader reader) throws IOException {
        int ch = 0;
        do {
            ch = reader.read();
        } while (isIndentLetter(ch));
        return ch;
    }

    public static boolean isDigitComponent(int ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    public static String readEffectiveString(Reader reader, int eliminator) throws IOException {
        StringBuilder result = new StringBuilder();
        int ch = 0;
        while ((ch = reader.read()) != eliminator) {
            if (ch == '\\') {
                ch = reader.read();
                //if(ch =='\\') ch = '\\'  not use
                if (ch == 't') ch = '\t';
                if (ch == 'n') ch = '\n';
            }
            result.append((char) ch);
        }
        return result.toString();
    }

    /**
     * Read a value/identifier until it was 'eliminated', it wouldn't consume the eliminator
     *
     * @param reader the source Reader (which is ought to be mark-supported)
     * @return the string that has been read
     * @throws IOException - if ever, the reader has encountered a problem, or is not mark-supported
     * @see java.io.BufferedReader
     */
    public static String readUntilEliminate(Reader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        int ch = 0;
        reader.mark(1);
        while (!isEliminator(ch = reader.read())) {
            result.append((char) ch);
            reader.mark(1);
        }
        reader.reset();
        return result.toString();
    }

    /**
     * Parse a raw string into generic nodes, it includes:
     * <p>
     * BigIntegerNode, BigDecimalNode, DoubleNode, IntegerNode, LongNode and ByteNode
     * <p>
     * note that StringNode (and its related ones) or BlobNode would not be processed here
     *
     * @param key the key (identifier) of node
     * @param raw the raw type to be resolved
     * @return the resolved node
     */
    public static EditableNode resolveRawType(String key, String raw) {
        EditableNode n = null;
        if (raw.equals("null")) {
            n = new IntNode(key, null);
        } else if (raw.chars().allMatch(ParserUtils::isDigitComponent)
                && raw.chars().filter(c -> c == '.').count() <= 1) {
            BigDecimal decimal = new BigDecimal(raw);
            if (decimal.scale() == 0) { // treated as integer
                BigInteger integer = decimal.toBigInteger();
                if (integer.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0) {
                    if (integer.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
                        if (integer.compareTo(BigInteger.valueOf(Byte.MAX_VALUE)) < 0) { // cancelled short check
                            n = new ByteNode(key, integer.byteValue());
                        } else n = new IntNode(key, integer.intValue());
                    } else n = new LongNode(key, integer.longValue());
                } else n = new BigIntegerNode(key, integer);
            } else {
                Double testDouble = decimal.doubleValue();
                if (testDouble != Double.NEGATIVE_INFINITY && testDouble != Double.POSITIVE_INFINITY)
                    n = new DoubleNode(key, testDouble);
                else n = new BigDecimalNode(key, decimal);
            }
        }
        return n;
    }

}
