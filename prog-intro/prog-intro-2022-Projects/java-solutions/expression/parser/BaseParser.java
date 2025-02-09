package expression.parser;

public class BaseParser {
    public static final char END = 0;

    protected CharSource sourse;
    private char ch;

    public BaseParser(CharSource sourse) {
        this.sourse = sourse;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = sourse.hasNext() ? sourse.next() : END;
        return result;
    }

    protected boolean take(char expected) {
        if (ch == expected) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected char look() {
        return ch;
    }

    protected boolean look(char expected) {
        return ch == expected;
    }

    protected void expect(String chars) {
        for (char c : chars.toCharArray()) {
            expect(c);
        }
    }

    protected void expect(char expected) {
        if (!take(expected)) {
            throw error("Expected: " + expected + ", found: '" + ch + "'");
        }
    }

    protected IllegalArgumentException error(String message) {
        return sourse.error(message);
    }
    protected boolean eof() {
        return ch == END;
    }

}
