package expression;

public class Conjunction extends AbstractBinaryOperation {
    public Conjunction(MathLogicExpression leftOperand, MathLogicExpression rightOperand) {
        super(leftOperand, rightOperand, "&");
    }
}
