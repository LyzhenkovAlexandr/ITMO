package expression;

public class Count implements AnyOperation {
    private final AnyOperation ex;

    public Count(AnyOperation ex) {
        this.ex = ex;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.bitCount(ex.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "count(" + ex + ")";
    }
}
