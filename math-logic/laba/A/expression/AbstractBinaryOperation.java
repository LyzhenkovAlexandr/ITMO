package expression;

public abstract class AbstractBinaryOperation implements Operation {
    private final MathLogicExpression leftOperand;
    private final MathLogicExpression rightOperand;
    private final String turn;

    public AbstractBinaryOperation(MathLogicExpression leftOperand, MathLogicExpression rightOperand, String turn) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.turn = turn;
    }

    @Override
    public String toStringFormat() {
        return "(" + turn + "," + leftOperand.toStringFormat() + "," + rightOperand.toStringFormat() + ")";
    }
}
