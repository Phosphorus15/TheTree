package net.steepout.ttree;

import net.steepout.ttree.data.*;

public enum NodeType {

    TYPE_HEADER(false, TreeRoot.class), TYPE_DATA_LIST(ListNode.class),

    TYPE_STRING(StringNode.class), TYPE_BYTE(ByteNode.class), TYPE_INT16(ShortNode.class),

    TYPE_INT32(IntNode.class), TYPE_INT64(LongNode.class), TYPE_FLOAT(FloatNode.class), TYPE_BLOB(BlobNode.class),

    TYPE_BIG_INTEGER(BigIntegerNode.class), TYPE_BIG_DECIMAL(BigDecimalNode.class),

    TYPE_ANNOTATIONS(AnnotationsNode.class),

    TYPE_DOUBLE_FLOAT(DoubleNode.class), TYPE_IDENTIFIER(IdentifierNode.class),

    TYPE_OTHER(false), TYPE_OTHER_DATA(false);

    public boolean isDataType() {
        return dataType;
    }

    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    boolean dataType;

    Class<? extends EditableNode> defaultInstance;

    NodeType(boolean isData, Class<? extends EditableNode> defaultInstance) {
        dataType = isData;
        this.defaultInstance = defaultInstance;
    }

    NodeType(boolean isData) {
        this(isData, null);
    }

    NodeType(Class<? extends EditableNode> defaultInstance) {
        this(true, defaultInstance);
    }

}
