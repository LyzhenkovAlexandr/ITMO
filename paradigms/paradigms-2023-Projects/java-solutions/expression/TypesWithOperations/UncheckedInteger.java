package expression.TypesWithOperations;


public class UncheckedInteger implements OperationWithType<Integer> {

    @Override
    public Integer add(Integer num1, Integer num2) {
        return num1 + num2;
    }

    @Override
    public Integer subtract(Integer num1, Integer num2) {
        return num1 - num2;
    }

    @Override
    public Integer divide(Integer num1, Integer num2) {
        return num1 / num2;
    }

    @Override
    public Integer multiply(Integer num1, Integer num2) {
        return num1 * num2;
    }

    @Override
    public Integer count(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer negative(Integer x) {
        return -x;
    }

    @Override
    public Integer valueOf(String s) {
        return Integer.parseInt(s);
    }
}
