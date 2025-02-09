package expression.parser;

public class StringCharSource implements CharSource {
    private final String string;
    private int pos;
    private int oldPosChar;
    private char oldChar;

    public StringCharSource(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }

    @Override
    public void saveChar() {
        oldPosChar = pos;
        oldChar = string.charAt(pos - 1);
    }

    @Override
    public char returnChar() {
        pos = oldPosChar;
        return oldChar;
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException("pos: " + message);
    }

    @Override
    public String toString() {
        return string;
    }
}