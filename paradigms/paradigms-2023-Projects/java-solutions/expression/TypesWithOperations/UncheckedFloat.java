package expression.TypesWithOperations;

public class UncheckedFloat implements OperationWithType<Float>{
    @Override
    public Float add(Float num1, Float num2) {
        return num1 + num2;
    }

    @Override
    public Float subtract(Float num1, Float num2) {
        return num1 - num2;
    }

    @Override
    public Float divide(Float num1, Float num2) {
        return num1 / num2;
    }

    @Override
    public Float multiply(Float num1, Float num2) {
        return num1 * num2;
    }

    @Override
    public Float count(Float x) {
        return null;
    }

    @Override
    public Float negative(Float x) {
        return -x;
    }

    @Override
    public Float valueOf(String s) {
        return Float.valueOf(s);
    }
}
