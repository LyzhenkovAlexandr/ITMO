package expression.exceptions;

import expression.Add;
import expression.AnyOperation;

public class CheckedAdd extends Add {
    public CheckedAdd(AnyOperation num1, AnyOperation num2) {
        super(num1, num2);
    }

    @Override
    protected int operation(int left, int right) {
        if (right > 0 && Integer.MAX_VALUE - right < left || right < 0 && Integer.MIN_VALUE - right > left) {
            throw new OverflowException();
        } else {
            return left + right;
        }
    }
}
