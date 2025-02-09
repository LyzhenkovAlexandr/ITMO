package expression;

import java.util.Objects;

public class Variable implements AnyOperation {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }
    
    @Override
    public int evaluate(int x, int y, int z) {
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
        if (obj instanceof final Variable variable1) {
            return variable.equals(variable1.variable);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }
}
