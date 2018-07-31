package net.steepout.ttree.utils;

import java.util.Objects;

public class Pair<A, B> implements Comparable<Pair<A, B>> {

    private A first;

    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        if (first instanceof Comparable) {
            return ((Comparable) first).compareTo(o.first);
        }
        if (second instanceof Comparable) {
            return ((Comparable) second).compareTo(o.second);
        }
        throw new RuntimeException("Unable to compare !");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair)
            return (Objects.equals(first, ((Pair) obj).first) && Objects.equals(second, ((Pair) obj).second));
        return super.equals(obj);
    }
}
