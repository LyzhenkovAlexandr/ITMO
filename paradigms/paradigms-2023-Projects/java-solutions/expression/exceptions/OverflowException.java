package expression.exceptions;

public class OverflowException extends RuntimeException {
    public OverflowException() {
        super("overflow");
    }

    public OverflowException(String message) {
        super(message);
    }
}
