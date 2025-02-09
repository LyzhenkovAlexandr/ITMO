package expression.TypesWithOperations;

public interface OperationWithType<T extends Number> {
    T add(T num1, T num2);
    T subtract(T num1, T num2);
    T divide(T num1, T num2);
    T multiply(T num1, T num2);
    T count(T x);
    T negative(T x);
    T valueOf(String s);
}