package expression.exceptions;

import expression.AnyOperation;
import expression.Negative;

public class CheckedNegate extends Negative {
    public CheckedNegate(AnyOperation ex) {
        super(ex);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operation(-super.evaluate(x, y, z));
    }

    protected int operation(int x) {
        if (x == Integer.MIN_VALUE)  {
            throw new OverflowException();
        }
        return -x;
    }
}
