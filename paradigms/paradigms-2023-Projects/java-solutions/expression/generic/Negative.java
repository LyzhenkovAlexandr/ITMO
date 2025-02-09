package expression.generic;

import expression.TypesWithOperations.OperationWithType;

public class Negative<T extends Number> implements AnyOperation<T> {

    private final AnyOperation<T> ex;
    private final OperationWithType<T> type;

    public Negative(AnyOperation<T> ex, OperationWithType<T> type) {
        this.ex = ex;
        this.type = type;
    }
    @Override
    public T evaluate(T x, T y, T z) {
        return type.negative(ex.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "-" + "(" + ex + ")";
    }
}
