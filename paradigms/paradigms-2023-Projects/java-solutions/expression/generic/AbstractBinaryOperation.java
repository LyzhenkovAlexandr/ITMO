package expression.generic;

import expression.TypesWithOperations.OperationWithType;

import java.util.Objects;


public abstract class AbstractBinaryOperation<T extends Number> implements AnyOperation<T> {
    private final AnyOperation<T> num1;
    private final AnyOperation<T> num2;
    protected final OperationWithType<T> type;

    public AbstractBinaryOperation(AnyOperation<T> num1, AnyOperation<T> num2, OperationWithType<T> type) {
        this.num1 = num1;
        this.num2 = num2;
        this.type = type;
    }

    public abstract String getOperation();

    protected abstract T operation(T left, T right);

    public T evaluate(T x, T y, T z) {
        return operation(num1.evaluate(x, y, z), num2.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AbstractBinaryOperation<?> that) {
            return (that.getOperation().equals(this.getOperation())) && (that.num1.equals(this.num1))
                    && (that.num2.equals(this.num2));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num1, num2, this.getClass(), type);
    }

    @Override
    public String toString() {
        return "(" + num1 + this.getOperation() + num2 + ")";
    }
}