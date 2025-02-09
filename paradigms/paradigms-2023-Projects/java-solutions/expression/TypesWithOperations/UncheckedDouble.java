package expression.TypesWithOperations;

public class UncheckedDouble implements OperationWithType<Double> {
    @Override
    public Double add(Double num1, Double num2) {
        return num1 + num2;
    }

    @Override
    public Double subtract(Double num1, Double num2) {
        return num1 - num2;
    }

    @Override
    public Double divide(Double num1, Double num2) {
        return num1 / num2;
    }

    @Override
    public Double multiply(Double num1, Double num2) {
        return num1 * num2;
    }

    @Override
    public Double count(Double x) {
        throw new IllegalArgumentException();
    }

    @Override
    public Double negative(Double x) {
        return -x;
    }

    @Override
    public Double valueOf(String s) {
        return Double.parseDouble(s);
    }
}
