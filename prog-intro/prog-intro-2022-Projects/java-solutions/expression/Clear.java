package expression;

public class Clear extends AbstractBinaryOperation {

    public Clear(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    public String getOperation() {
        return " clear ";
    }

    @Override
    protected int operation(int left, int right) {
        return left & ~(1 << right);
    }
}
