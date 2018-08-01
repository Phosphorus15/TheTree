package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.NodeType;
import net.steepout.ttree.TreeManager;
import net.steepout.ttree.TreeNode;
import net.steepout.ttree.TreeRoot;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.utils.Bits;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Arbre is a specially designed structure for serializing data (Though very simple)
 * <p>
 * Header Structure : # -> () Means necessary , [] Means Optional
 * (AB1EFFFF) Magic Number (int32) File version (int64) Unix timestamp [OPH] [MD5 Hash] digest
 */

public class ArbreProcessor extends TreeProcessor {

    static final int SOF = 0x0; // Start of file
    static final int SOS = 0x1; // Start of section
    public static final int NOV = 0x2; // No value
    public static final int HVV = 0x3; // Have value
    static final int EOS = 0x4; // End of section
    static final int EFV = 0x5; // Effective value
    static final int OPH = 0x6; // Optional Hash (Md5)
    static final int EOF = 0x7; // End of file
    public static final int NML = 0x8; // Normal length value (int32)
    public static final int EXL = 0x9; // Extra length value (int64)
    static final int SOL = 0xa; // Start of List
    static final int EOL = 0xb; // End of List
    static final int MAGIC_NUMBER = 0xAB1EFFFF;

    boolean verifyDigest = true;

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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(SOF);
        try {
            writeNode(root, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream.write(EOF);
        byte[] data = stream.toByteArray();
        ByteArrayOutputStream header = new ByteArrayOutputStream();
        try {
            header.write(Bits.wrapInt(MAGIC_NUMBER));
            header.write(Bits.wrapInt(TreeManager.VERSION));
            header.write(Bits.wrapLong(System.currentTimeMillis() / 1000));
            if (verifyDigest) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    digest.update(data);
                    byte[] md = digest.digest();
                    header.write(OPH);
                    header.write(md);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer result = ByteBuffer.allocate(stream.size() + header.size());
        result.put(header.toByteArray());
        result.put(stream.toByteArray());
        return result;
    }

    public void writeNode(TreeNode node, OutputStream stream) throws IOException {
        if (node.getType() == NodeType.TYPE_HEADER) {
            stream.write(SOS);
            stream.write(Bits.wrapString(node.getName()));
            for (TreeNode n : node.subNodes()) writeNode(n, stream);
            stream.write(EOS);
        } else if (node.getType().isDataType()) {
            stream.write(node.getType().ordinal());
            stream.write(Bits.wrapString(node.getName()));
            if (node.getValue() == null) stream.write(NOV);
            else
                switch (node.getType()) {
                    case TYPE_BYTE:
                        stream.write(node.asByte());
                        break;
                    case TYPE_INT16:
                        stream.write(Bits.wrapShort((Short) node.getValue()));
                        break;
                    case TYPE_INT32:
                        stream.write(Bits.wrapInt(node.asInt()));
                        break;
                    case TYPE_INT64:
                        stream.write(Bits.wrapLong(node.asLong()));
                        break;
                    case TYPE_FLOAT:
                        stream.write(Bits.wrapFloat((Float) node.getValue()));
                        break;
                    case TYPE_DOUBLE_FLOAT:
                        stream.write(Bits.wrapDouble((Double) node.getValue()));
                        break;
                    case TYPE_STRING:
                    case TYPE_ANNOTATIONS:
                    case TYPE_IDENTIFIER:
                    case TYPE_BIG_DECIMAL:
                        stream.write(Bits.wrapString(node.getValue().toString()));
                        break;
                    case TYPE_BIG_INTEGER:
                        stream.write(Bits.wrapBlob(((BigInteger) node.getValue()).toByteArray()));
                        break;
                    case TYPE_BLOB:
                        stream.write(Bits.wrapBlob((byte[]) node.getValue()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unidentified data object " + node.getClass().getName());
                }
        } else if (node.getType() == NodeType.TYPE_DATA_LIST) {

        } else {
            // FIXME further process
        }
    }

}
