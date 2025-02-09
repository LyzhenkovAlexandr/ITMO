package expression.parser;

public class StringCharSource implements CharSource {

    private final String string;
    private int pos;

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
    public char nextLook() {
        return string.charAt(pos == 0 ? pos : pos-1);
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