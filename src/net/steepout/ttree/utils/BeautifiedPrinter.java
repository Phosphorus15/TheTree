package net.steepout.ttree.utils;

public class BeautifiedPrinter {

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
                for (int j = 0; j < buffer.length; j++) {
                    if (Character.isLetter(buffer[j])) System.out.print((char) buffer[j]);
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
