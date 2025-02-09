package expression;

public class Negative implements AnyOperation {

    private final AnyOperation ex;

    public Negative(AnyOperation ex) {
        this.ex = ex;
    }

    @Override
    public int evaluate(int x) {
        return -ex.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -ex.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-" + "(" + ex + ")";
    }

}
