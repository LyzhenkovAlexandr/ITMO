package expression;

public class Subtract extends AbstractBinaryOperation {

    public Subtract(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    public String getOperation() {
        return " - ";
    }

    @Override
    protected int operation(int left, int right) {
        return left - right;
    }
}
