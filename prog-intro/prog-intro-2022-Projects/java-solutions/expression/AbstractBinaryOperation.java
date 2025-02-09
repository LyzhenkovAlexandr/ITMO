package expression;

import java.util.Objects;


public abstract class AbstractBinaryOperation implements AnyOperation {
    private final AnyOperation num1;
    private final AnyOperation num2;

    public AbstractBinaryOperation(AnyOperation num1, AnyOperation num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public abstract String getOperation();

    protected abstract int operation(int left, int right);

    public int evaluate(int x) {
        return operation(num1.evaluate(x), num2.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return operation(num1.evaluate(x, y, z), num2.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AbstractBinaryOperation that) {
            return (that.getOperation().equals(this.getOperation())) && (that.num1.equals(this.num1))
                    && (that.num2.equals(this.num2));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num1, num2, this.getClass());
    }

    @Override
    public String toString() {
        return "(" + num1 + this.getOperation() + num2 + ")";
    }
}
