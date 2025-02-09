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

    protected boolean take(String chars) {
        sourse.saveChar();
        for (char c : chars.toCharArray()) {
            if (!take(c)) {
                ch = sourse.returnChar();
                return false;
            }
        }
        return true;
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

    protected boolean isLetter(final char one, final char two) {
        return one <= ch && ch <= two;
    }
    protected boolean isDigit() {
        return '0' <= ch && ch <= '9';
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }

    protected IllegalArgumentException error(String message) {
        return sourse.error(message);
    }

    protected boolean eof() {
        return ch == END;
    }
}