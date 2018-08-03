package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.*;
import net.steepout.ttree.data.ListNode;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.utils.Bits;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The Arbre is a specially designed structure for serializing data (Though very simple)
 * <p>
 * Commonly, we suggest that a lar file or blob should be compressed by GZIP
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
    public static final int MNL = 0xd; // Minimal length value (int16)
    public static final int NML = 0x8; // Normal length value (int32)
    public static final int EXL = 0x9; // Extra length value (int64)
    static final int SOL = 0xa; // Start of List
    static final int EOL = 0xb; // End of List
    static final int MKD = 0xc; // Mark of Data
    static final int MAGIC_NUMBER = 0xAB1EFFFF;

    public boolean isVerifyDigest() {
        return verifyDigest;
    }

    public void setVerifyDigest(boolean verifyDigest) {
        this.verifyDigest = verifyDigest;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    boolean verifyDigest = true;

    boolean compress = true;

    static {
        TreeManager.registerProcessor(new ArbreProcessor());
        TreeManager.registerAliases("L'Arbre", "LeArbre", "lar", "LArbre", "TheTree", "the tree", "arbre");
    }

    private ArbreProcessor() {
        super("L'Arbre");
    }

    @Override
    public LARAttributiveRootNode parse(InputStream stream) throws IOException {
        if (compress) return parseUncompressed(new GZIPInputStream(stream));
        else return parseUncompressed(stream);
    }

    public LARAttributiveRootNode parseUncompressed(InputStream stream) throws IOException {
        ByteArrayOutputStream cache = new ByteArrayOutputStream();
        int i = 0;
        while ((i = stream.read()) != -1)
            cache.write(i);
        ByteBuffer buffer = ByteBuffer.wrap(cache.toByteArray());
        buffer.order(ByteOrder.BIG_ENDIAN);
        cache.close();
        if (buffer.limit() <= 8 || buffer.getInt() != MAGIC_NUMBER)
            raiseInvalid();
        LARAttributiveRootNode root = new LARAttributiveRootNode(null);
        root.setCreatedVersion(buffer.getInt());
        root.setTimeStamp(buffer.getLong());
        byte[] optionalHash = null;
        if (buffer.get(buffer.position()) == OPH) {
            buffer.get(); // skip OPH label
            optionalHash = new byte[16];
            buffer.get(optionalHash);
        }
        if (buffer.get() != SOF) raiseInvalid();
        if (buffer.get() != SOS) return null;
        root.setName(Bits.getEffectiveString(buffer));
        try {
            parseNodes(root, buffer, false);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (buffer.get() != EOS) raiseInvalid();
        if (buffer.get() != EOF) raiseInvalid();
        return root;
    }

    private void parseNodes(EditableNode father, ByteBuffer buffer, boolean forceList) throws InvalidObjectException, IllegalAccessException, InstantiationException, InvocationTargetException {
        while (buffer.get(buffer.position()) != EOS && buffer.get(buffer.position()) != EOL) {
            byte code = buffer.get();
            String name = Bits.getEffectiveString(buffer);
            if (code == MKD) {
                NodeType typeEnum = NodeType.values()[buffer.get()];
                if (!typeEnum.isDataType()) raiseInvalid();
                EditableNode node = TreeManager.emptyNodeByClass(typeEnum.getDefaultInstance());
                if (!forceList) node.setName(name);
                byte status = buffer.get();
                if (status == NOV) {
                    node.setValue(null);
                } else if (status == HVV) {
                    switch (typeEnum) {
                        case TYPE_BYTE:
                            node.setValue(buffer.get());
                            break;
                        case TYPE_INT16:
                            node.setValue(buffer.getShort());
                            break;
                        case TYPE_INT32:
                            node.setValue(buffer.getInt());
                            break;
                        case TYPE_INT64:
                            node.setValue(buffer.getLong());
                            break;
                        case TYPE_FLOAT:
                            node.setValue(buffer.getFloat());
                            break;
                        case TYPE_DOUBLE_FLOAT:
                            node.setValue(buffer.getDouble());
                            break;
                        case TYPE_STRING:
                        case TYPE_ANNOTATIONS:
                        case TYPE_IDENTIFIER:
                        case TYPE_BIG_DECIMAL:
                            String rawValue = Bits.getEffectiveString(buffer);
                            if (typeEnum != NodeType.TYPE_BIG_DECIMAL) {
                                node.setValue(rawValue);
                            } else {
                                node.setValue(new BigDecimal(rawValue));
                            }
                            break;
                        case TYPE_BIG_INTEGER:
                        case TYPE_BLOB:
                            byte[] rawData = Bits.getNonEmptyBlob(buffer);
                            if (typeEnum != NodeType.TYPE_BIG_INTEGER) {
                                node.setValue(rawData);
                            } else {
                                node.setValue(new BigInteger(rawData));
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Unidentified data object " + node.getClass().getName());
                    }
                } else raiseInvalid();
                father.subNodes().add(node);
            } else if (code == SOL || code == SOS) {
                TreeRoot listRoot = (code == SOL) ? new ListNode() : new TreeRoot();
                if (!forceList)
                    listRoot.setName(name);
                parseNodes(listRoot, buffer, code == SOL);
                if ((code == SOL && buffer.get() != EOL) || (code == SOS && buffer.get() != EOS)) raiseInvalid();
                father.subNodes().add(listRoot);
            }
        }
    }

    private void raiseInvalid() throws InvalidObjectException {
        throw new InvalidObjectException("Not a valid lar file (try switch compress mode)");
    }

    @Override
    public ByteBuffer serialize(TreeRoot root) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(SOF);
        try {
            writeNode(root, stream, false);
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
        if (compress) {
            try {
                ByteArrayOutputStream zipped = new ByteArrayOutputStream();
                GZIPOutputStream outputStream = new GZIPOutputStream(zipped);
                outputStream.write(result.array());
                outputStream.close();
                return ByteBuffer.wrap(zipped.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("Compression failed, try cancel compressing", e);
            }
        }
        return result;
    }

    private void writeNode(TreeNode node, OutputStream stream, boolean forceList) throws IOException {
        if (node.getType() == NodeType.TYPE_HEADER) {
            stream.write(SOS);
            if (forceList)
                stream.write(NOV);
            else
                stream.write(Bits.wrapString(node.getName()));
            for (TreeNode n : node.subNodes()) writeNode(n, stream, false);
            stream.write(EOS);
        } else if (node.getType().isDataType()) {
            stream.write(MKD);
            if (forceList)
                stream.write(NOV);
            else
                stream.write(Bits.wrapString(node.getName()));
            stream.write(node.getType().ordinal());
            if (node.getValue() == null) stream.write(NOV);
            else {
                stream.write(HVV);
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
                        stream.write(Bits.wrapNonEmptyBlob(((BigInteger) node.getValue()).toByteArray()));
                        break;
                    case TYPE_BLOB:
                        stream.write(Bits.wrapNonEmptyBlob((byte[]) node.getValue()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unidentified data object " + node.getClass().getName());
                }
            }
        } else if (node.getType() == NodeType.TYPE_DATA_LIST) {
            stream.write(SOL);
            if (forceList)
                stream.write(NOV);
            else
                stream.write(Bits.wrapString(node.getName()));
            for (TreeNode n : node.subNodes()) writeNode(n, stream, true); // get rid of the "name" property
            stream.write(EOL);
        } else {
            // FIXME further process
        }
    }

}
