package net.steepout.ttree.utils;

public class Either<A, B> implements Comparable<Either<A, B>> {

    private A left = null;

    private B right = null;

    public Either(A left) {
        this(left, null);
    }

    public Either(A left, B right) {
        if (left != null && right != null) throw new RuntimeException("Either left or right should be null");
        if (left == null && right == null) throw new RuntimeException("Either left and right should not be null");
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return left;
    }

    public void setLeft(A left) {
        if (left != null)
            this.left = left;
    }

    public B getRight() {
        return right;
    }

    public void setRight(B right) {
        if (right != null)
            this.right = right;
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    @Override
    public int compareTo(Either<A, B> o) {
        if (isLeft() == o.isLeft()) {
            Object a = (isLeft()) ? left : right;
            Object b = (o.isLeft()) ? o.left : o.right;
            if (a instanceof Comparable && b instanceof Comparable) {
                return ((Comparable) a).compareTo(b);
            } else throw new RuntimeException("Type must be instance of Comparable");
        }
        return (isLeft()) ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Either) {
            if (obj.getClass().equals(getClass())) {
                Either<A, B> o = (Either<A, B>) obj;
                if (isLeft() == ((Either) obj).isLeft()) {
                    Object a = (isLeft()) ? left : right;
                    Object b = (o.isLeft()) ? o.left : o.right;
                    return a.equals(b);
                }
            }
        }
        return super.equals(obj);
    }

}
