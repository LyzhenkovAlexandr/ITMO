package expression;

public class Inversion extends AbstractUnaryOperation {
    public Inversion(MathLogicExpression operand) {
        super(operand, "!");
    }
}
