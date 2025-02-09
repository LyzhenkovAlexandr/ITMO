package expression.generic;


public interface AnyOperation<T extends Number> {
    T evaluate(T x, T y, T z);
}