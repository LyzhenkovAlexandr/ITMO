package expression;

public class Set extends AbstractBinaryOperation {

    public Set(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    public String getOperation() {
        return " set ";
    }

    @Override
    protected int operation(int left, int right) {
        return left | (1 << right);
    }
}
