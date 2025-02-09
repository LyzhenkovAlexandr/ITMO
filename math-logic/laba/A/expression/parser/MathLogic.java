package expression.parser;

import expression.*;

public final class MathLogic {
    private MathLogic() {
    }

    public static MathLogicExpression parse(final String string) {
        MathLogicParser parser = new MathLogicParser(new StringCharSource(string));
        return parser.parse();
    }

    private static class MathLogicParser extends BaseParser {
        private Token token;

        public MathLogicParser(CharSource sourse) {
            super(sourse);
        }

        public MathLogicExpression parse() {
            getToken();
            MathLogicExpression expr = fourthLevel();
            if (token != Token.EOF) {
                throw new IllegalArgumentException("Unexpected char");
            }
            return expr;
        }

        private MathLogicExpression fourthLevel() {
            MathLogicExpression expr = thirdLevel();
            while (true) {
                switch (token) {
                    case IMPLICATION -> {
                        getToken();
                        expr = new Implication(expr, fourthLevel());
                    }
                    default -> {
                        return expr;
                    }
                }
            }
        }

        private MathLogicExpression thirdLevel() {
            MathLogicExpression expr = secondLevel();
            while (true) {
                switch (token) {
                    case DISJUNCTION -> {
                        getToken();
                        expr = new Disjunction(expr, secondLevel());
                    }
                    default -> {
                        return expr;
                    }
                }
            }
        }

        private MathLogicExpression secondLevel() {
            MathLogicExpression expr = firstLevel();
            while (true) {
                switch (token) {
                    case CONJUNCTION -> {
                        getToken();
                        expr = new Conjunction(expr, firstLevel());
                    }
                    default -> {
                        return expr;
                    }
                }
            }
        }

        private MathLogicExpression firstLevel() {
            switch (token) {
                case VARIABLE -> {
                    return new Variable(parseVariable());
                }
                case INVERSION -> {
                    getToken();
                    return new Inversion(firstLevel());
                }
                case OPEN_BRACKET -> {
                    getToken();
                    MathLogicExpression expr = fourthLevel();
                    if (token != Token.CLOSED_BRACKET) {
                        throw new IllegalArgumentException("expected ')'");
                    }
                    getToken();
                    return expr;
                }
                case IMPLICATION, DISJUNCTION, EOF, CONJUNCTION, CLOSED_BRACKET ->
                        throw new IllegalArgumentException("The operation is incorrectly drafted");
                default -> throw new IllegalArgumentException("Wrong argument");
            }
        }

        private String parseVariable() {
            StringBuilder sb = new StringBuilder();
            while (isLetter('A', 'Z') || isDigit() || isASCIIChar('’')) {
                sb.append(take());
            }
            getToken();
            return sb.toString();
        }

        private void getToken() {
            skipWhitespace();
            if (eof()) {
                token = Token.EOF;
            } else if (take('(')) {
                token = Token.OPEN_BRACKET;
            } else if (take(')')) {
                token = Token.CLOSED_BRACKET;
            } else if (take('!')) {
                token = Token.INVERSION;
            } else if (take('&')) {
                token = Token.CONJUNCTION;
            } else if (take('|')) {
                token = Token.DISJUNCTION;
            } else if (take("->")) {
                token = Token.IMPLICATION;
            } else if (isLetter('A', 'Z')) {
                token = Token.VARIABLE;
            } else {
                throw new IllegalArgumentException("False token");
            }
        }

    }
}
