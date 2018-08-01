package net.steepout.ttree.parser.l_arbre;

import net.steepout.ttree.TreeRoot;

public class LARAttributiveRoot extends TreeRoot {

    public LARAttributiveRoot(String caption) {
        super(caption);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getCreatedVersion() {
        return createdVersion;
    }

    public void setCreatedVersion(int createdVersion) {
        this.createdVersion = createdVersion;
    }

    long timeStamp;

    int createdVersion;

}
