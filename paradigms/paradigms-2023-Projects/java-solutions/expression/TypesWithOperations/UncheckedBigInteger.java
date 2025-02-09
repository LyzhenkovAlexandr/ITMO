package expression.TypesWithOperations;

import java.math.BigInteger;

public class UncheckedBigInteger implements OperationWithType<BigInteger> {
    @Override
    public BigInteger add(BigInteger num1, BigInteger num2) {
        return num1.add(num2);
    }

    @Override
    public BigInteger subtract(BigInteger num1, BigInteger num2) {
        return num1.subtract(num2);
    }

    @Override
    public BigInteger divide(BigInteger num1, BigInteger num2) {
        return num1.divide(num2);
    }

    @Override
    public BigInteger multiply(BigInteger num1, BigInteger num2) {
        return num1.multiply(num2);
    }

    @Override
    public BigInteger count(BigInteger x) {
        throw new IllegalArgumentException();
    }

    @Override
    public BigInteger negative(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger valueOf(String s) {
        return new BigInteger(s);
    }
}
