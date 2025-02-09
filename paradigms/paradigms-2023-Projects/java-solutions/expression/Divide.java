package expression;

public class Divide extends AbstractBinaryOperation {

    public Divide(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    public String getOperation() {
        return " / ";
    }

    @Override
    protected int operation(int left, int right) {
        return left / right;
    }
}
