package expression.generic;

import expression.TypesWithOperations.OperationWithType;

public class Multiply<T extends Number> extends AbstractBinaryOperation<T> {

    public Multiply(AnyOperation<T> num1, AnyOperation<T> num2, OperationWithType<T> type) {
        super(num1, num2, type);
    }

    @Override
    public String getOperation() {
        return " * ";
    }

    @Override
    protected T operation(T left, T right) {
        return type.multiply(left, right);
    }
}