package expression;

public abstract class AbstractUnaryOperation implements Operation {
    private final MathLogicExpression operand;
    private final String turn;

    public AbstractUnaryOperation(MathLogicExpression operand, String turn) {
        this.operand = operand;
        this.turn = turn;
    }

    @Override
    public String toStringFormat() {
        return "(" + turn + operand.toStringFormat() + ")";
    }
}
