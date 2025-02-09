package expression.exceptions;

import expression.AnyOperation;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    protected int operation(int left, int right) {
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException();
        } else if (right == 0) {
            throw new DBZException();
        } else {
            return left / right;
        }
    }
}
