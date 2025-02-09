package expression.TypesWithOperations;

public class UncheckedShort implements OperationWithType<Short> {
    @Override
    public Short add(Short num1, Short num2) {
        return (short) (num1 + num2);
    }

    @Override
    public Short subtract(Short num1, Short num2) {
        return (short) (num1 - num2);
    }

    @Override
    public Short divide(Short num1, Short num2) {
        return (short) (num1 / num2);
    }

    @Override
    public Short multiply(Short num1, Short num2) {
        return (short) (num1 * num2);
    }

    @Override
    public Short count(Short x) {
        return null;
    }

    @Override
    public Short negative(Short x) {
        return (short) -x;
    }

    @Override
    public Short valueOf(String s) {
        int temp = Integer.parseInt(s);
        if (temp >= Short.MIN_VALUE && temp <= Short.MAX_VALUE) {
            return Short.valueOf(s);
        } else if (temp > 0) {
            return (short) Math.floorMod(temp, Short.MIN_VALUE);
        }
        return (short) Math.floorMod(temp, -Short.MIN_VALUE);
    }
}
