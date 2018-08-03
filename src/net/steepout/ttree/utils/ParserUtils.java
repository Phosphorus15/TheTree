package net.steepout.ttree.utils;

import java.io.IOException;
import java.io.Reader;

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
        return isBracket(ch) != 0 || isIndentLetter(ch) || isStringEliminator(ch);
    }

    public static int readUntilValid(Reader reader) throws IOException {
        int ch = 0;
        do {
            ch = reader.read();
        } while (isIndentLetter(ch));
        return ch;
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
            result.append(ch);
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
        while (isEliminator(ch = reader.read())) {
            result.append(ch);
            reader.mark(1);
        }
        reader.reset();
        return result.toString();
    }

}
