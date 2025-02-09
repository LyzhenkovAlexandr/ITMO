package expression.generic;

import java.util.Objects;

public class Variable<T extends Number> implements AnyOperation<T> {
    private final String variable;
    public Variable(String variable) {
        this.variable = variable;
    }
    
    @Override
    public T evaluate(T x, T y, T z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Variable<? extends Number> variable1) {
            return variable.equals(variable1.variable);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }
}
