package expression;

public class Const implements AnyOperation {
    private final int x;

    public Const(int x) {
        this.x = x;
    }

    @Override
    public int evaluate(int x) {
        return this.x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.x;
    }

    @Override
    public String toString() {
        return String.valueOf(x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Const aConst) {
            return this.x == aConst.x;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x;
    }
}
