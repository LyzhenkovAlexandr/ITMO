package expression.TypesWithOperations;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

public class CheckedInteger extends UncheckedInteger {
    @Override
    public Integer add(Integer num1, Integer num2) {
        if (num2 > 0 && Integer.MAX_VALUE - num2 < num1 || num2 < 0 && Integer.MIN_VALUE - num2 > num1) {
            throw new OverflowException();
        } else {
            return num1 + num2;
        }
    }

    @Override
    public Integer subtract(Integer num1, Integer num2) {
        if (num2 > 0 && Integer.MIN_VALUE + num2 > num1 || num2 < 0 && Integer.MAX_VALUE + num2 < num1) {
            throw new OverflowException();
        } else {
            return num1 - num2;
        }
    }

    @Override
    public Integer divide(Integer num1, Integer num2) {
        if (num1 == Integer.MIN_VALUE && num2 == -1) {
            throw new OverflowException();
        } else if (num2 == 0) {
            throw new DBZException();
        } else {
            return num1 / num2;
        }
    }

    @Override
    public Integer multiply(Integer num1, Integer num2) {
        if (num1 == 0) {
            return 0;
        } else if (num1 > 0) {
            if (Integer.MIN_VALUE / num1 <= num2 && num2 <= Integer.MAX_VALUE / num1) {
                return num1 * num2;
            } else {
                throw new ArithmeticException("Overflow");
            }
        }
        if (num2 > Integer.MIN_VALUE && num1 == -1) {
            return num1 * num2;
        } else if (Integer.MAX_VALUE / num1 <= num2 && num2 <= Integer.MIN_VALUE / num1) {
            return num1 * num2;
        }
        throw new ArithmeticException("Overflow");
    }

    @Override
    public Integer negative(Integer x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -x;
    }
}
