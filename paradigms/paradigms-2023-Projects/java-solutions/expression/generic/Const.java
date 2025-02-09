package expression.generic;

import java.util.Objects;

public class Const<T extends Number> implements AnyOperation<T> {
    private final T x;

    public Const(T x) {
        this.x = x;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return this.x;
    }

    @Override
    public String toString() {
        return String.valueOf(x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Const<? extends Number> aConst) {
            return this.x == aConst.x;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x);
    }
}