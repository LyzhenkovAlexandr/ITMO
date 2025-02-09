package expression.exceptions;

import expression.AnyOperation;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    protected int operation(int left, int right) {
        if (left == 0) {
            return 0;
        } else if (left > 0) {
            if (Integer.MIN_VALUE / left <= right && right <= Integer.MAX_VALUE / left) {
                return left * right;
            } else {
                throw new OverflowException("Overflow");
            }
        }
        if (right > Integer.MIN_VALUE && left == -1) {
            return left * right;
        } else if (Integer.MAX_VALUE / left <= right && right <= Integer.MIN_VALUE / left) {
            return left * right;
        }
        throw new OverflowException("Overflow");
    }
}
