package expression;

public class Implication extends AbstractBinaryOperation {
    public Implication(MathLogicExpression leftOperand, MathLogicExpression rightOperand) {
        super(leftOperand, rightOperand, "->");
    }
}
