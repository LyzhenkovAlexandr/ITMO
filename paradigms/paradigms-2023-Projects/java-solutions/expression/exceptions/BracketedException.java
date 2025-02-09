package expression.exceptions;

public class BracketedException extends ParserException {
    public BracketedException() {
        super("expected '(' or ')'");
    }

    public BracketedException(String message) {
        super(message);
    }
}
