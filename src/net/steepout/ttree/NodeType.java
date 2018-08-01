package net.steepout.ttree;

public enum NodeType {

    TYPE_HEADER(false), TYPE_DATA_LIST(false),

    TYPE_STRING, TYPE_BYTE, TYPE_INT16,

    TYPE_INT32, TYPE_INT64, TYPE_FLOAT,

    TYPE_BIG_INTEGER, TYPE_BIG_DECIMAL, TYPE_ANNOTATIONS(false),

    TYPE_DOUBLE_FLOAT, TYPE_IDENTIFIER(false), TYPE_OTHER(false), TYPE_OTHER_DATA(false);

    public boolean isDataType() {
        return dataType;
    }

    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    boolean dataType;

    NodeType(boolean isData) {
        dataType = isData;
    }

    NodeType() {
        this(true);
    }

}
