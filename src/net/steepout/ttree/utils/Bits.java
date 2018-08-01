package net.steepout.ttree.utils;

import net.steepout.ttree.parser.l_arbre.ArbreProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Bits {

    public static boolean getBoolean(byte[] b, int off) {
        return b[off] != 0;
    }

    public static char getChar(byte[] b, int off) {
        return (char) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    public static short getShort(byte[] b, int off) {
        return (short) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    public static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF)) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off]) << 24);
    }

    public static float getFloat(byte[] b, int off) {
        return Float.intBitsToFloat(getInt(b, off));
    }

    public static long getLong(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL)) +
                ((b[off + 6] & 0xFFL) << 8) +
                ((b[off + 5] & 0xFFL) << 16) +
                ((b[off + 4] & 0xFFL) << 24) +
                ((b[off + 3] & 0xFFL) << 32) +
                ((b[off + 2] & 0xFFL) << 40) +
                ((b[off + 1] & 0xFFL) << 48) +
                (((long) b[off]) << 56);
    }

    public static double getDouble(byte[] b, int off) {
        return Double.longBitsToDouble(getLong(b, off));
    }

    public static void putBoolean(byte[] b, int off, boolean val) {
        b[off] = (byte) (val ? 1 : 0);
    }


    public static void putChar(byte[] b, int off, char val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }

    public static byte[] wrapChar(char val) {
        byte[] ch = new byte[2];
        putChar(ch, 0, val);
        return ch;
    }

    public static void putShort(byte[] b, int off, short val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }

    public static byte[] wrapShort(short val) {
        byte[] ch = new byte[2];
        putShort(ch, 0, val);
        return ch;
    }

    public static void putInt(byte[] b, int off, int val) {
        b[off + 3] = (byte) (val);
        b[off + 2] = (byte) (val >>> 8);
        b[off + 1] = (byte) (val >>> 16);
        b[off] = (byte) (val >>> 24);
    }

    public static byte[] wrapInt(int val) {
        byte[] ch = new byte[4];
        putInt(ch, 0, val);
        return ch;
    }

    public static void putFloat(byte[] b, int off, float val) {
        putInt(b, off, Float.floatToIntBits(val));
    }

    public static byte[] wrapFloat(float val) {
        return wrapInt(Float.floatToIntBits(val));
    }

    public static void putLong(byte[] b, int off, long val) {
        b[off + 7] = (byte) (val);
        b[off + 6] = (byte) (val >>> 8);
        b[off + 5] = (byte) (val >>> 16);
        b[off + 4] = (byte) (val >>> 24);
        b[off + 3] = (byte) (val >>> 32);
        b[off + 2] = (byte) (val >>> 40);
        b[off + 1] = (byte) (val >>> 48);
        b[off] = (byte) (val >>> 56);
    }

    public static byte[] wrapLong(long val) {
        byte[] ch = new byte[8];
        putLong(ch, 0, val);
        return ch;
    }

    public static void putDouble(byte[] b, int off, double val) {
        putLong(b, off, Double.doubleToLongBits(val));
    }

    public static byte[] wrapDouble(double val) {
        return wrapLong(Double.doubleToLongBits(val));
    }

    public static byte[] wrapString(String string) {
        if (string == null) return new byte[]{ArbreProcessor.NOV};
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        temp.write(ArbreProcessor.HVV);
        temp.write(ArbreProcessor.NML);
        try {
            temp.write(wrapInt(string.length()));
            temp.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp.toByteArray();
    }

    public static byte[] wrapBlob(byte[] byt) {
        if (byt == null) return new byte[]{ArbreProcessor.NOV};
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        temp.write(ArbreProcessor.HVV);
        temp.write(ArbreProcessor.NML);
        try {
            temp.write(wrapInt(byt.length));
            temp.write(byt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp.toByteArray();
    }

}
