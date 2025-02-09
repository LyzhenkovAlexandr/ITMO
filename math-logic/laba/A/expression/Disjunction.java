package expression;

public class Disjunction extends AbstractBinaryOperation {
    public Disjunction(MathLogicExpression leftOperand, MathLogicExpression rightOperand) {
        super(leftOperand, rightOperand, "|");
    }
}
