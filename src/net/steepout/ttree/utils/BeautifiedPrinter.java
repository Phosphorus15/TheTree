package net.steepout.ttree.utils;

public class BeautifiedPrinter {

    private static final String punctuations = "~`!@#$%^&*()_+-=[]{}\\|;:'\"<>,./?";

    public static void printBytes(byte[] bytes) {
        byte[] buffer = new byte[16];
        for (int i = 0, reline = 0; i < bytes.length; i++) {
            String ohex = Integer.toHexString(Byte.toUnsignedInt(bytes[i]));
            buffer[reline] = bytes[i];
            if (ohex.length() == 1) ohex = "0" + ohex;
            System.out.print(ohex + " ");
            if (++reline == 16) {
                reline = 0;
                System.out.flush();
                System.out.print(" | ");
                for (byte aBuffer : buffer) {
                    if (Character.isLetter(aBuffer)
                            || Character.isDigit(aBuffer)
                            || punctuations.indexOf(aBuffer) != -1
                            || Character.isSpaceChar(aBuffer))
                        System.out.print((char) aBuffer);
                    else System.out.print("?");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static String quotedString(String str) {
        str = str.replace("\n", "\\n")
                .replace("\r", "\\r").replace("\"", "\\\"");
        return "\"" + str + "\"";
    }

}
