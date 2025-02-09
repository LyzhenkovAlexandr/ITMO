package expression.exceptions;

public class DBZException extends RuntimeException {
    public DBZException() {
        super("division by zero");
    }

    public DBZException(String message) {
        super(message);
    }
}
