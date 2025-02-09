package expression;

public class Variable implements Operand {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toStringFormat() {
        return variable;
    }
}
