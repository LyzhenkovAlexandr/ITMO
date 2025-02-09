package expression.parser;


import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
        try {
            EParser parser = new EParser(new StringCharSource(expression));
            return parser.parse(false);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static class EParser extends BaseParser {
        private Token token;
        private StringBuilder constOrVariable = new StringBuilder();
        private boolean checkedOrNo;


        public EParser(CharSource sourse) {
            super(sourse);
        }

        public AnyOperation parse(final boolean checked) throws ParserException {
            checkedOrNo = checked;
            getToken();
            AnyOperation exp = fourthLevel();
            if (token != Token.EOF) {
                throw new IllegalArgumentException("Unexpected char");
            }
            return exp;
        }

        private AnyOperation fourthLevel() throws ParserException {
            AnyOperation exp = thirdLevel();
            while (true) {
                switch (token) {
                    case SET -> {
                        getToken();
                        exp = new Set(exp, thirdLevel());
                    }
                    case CLEAR -> {
                        getToken();
                        exp = new Clear(exp, thirdLevel());
                    }
                    default -> {
                        return exp;
                    }
                }
            }
        }

        private AnyOperation thirdLevel() throws ParserException {
            AnyOperation exp = secondLevel();
            while (true) {
                switch (token) {
                    case ADD -> {
                        getToken();
                        exp = (!checkedOrNo ? new Add(exp, secondLevel()) : new CheckedAdd(exp, secondLevel()));
                    }
                    case SUBTRACT -> {
                        getToken();
                        exp = (!checkedOrNo ? new Subtract(exp, secondLevel()) : new CheckedSubtract(exp, secondLevel()));
                    }
                    default -> {
                        return exp;
                    }
                }
            }
        }

        private AnyOperation secondLevel() throws ParserException {
            AnyOperation exp = firstLevel();
            while (true) {
                switch (token) {
                    case MULTIPLY -> {
                        getToken();
                        exp = (!checkedOrNo ? new Multiply(exp, firstLevel()) : new CheckedMultiply(exp, firstLevel()));
                    }
                    case DIVIDE -> {
                        getToken();
                        exp = (!checkedOrNo ? new Divide(exp, firstLevel()) : new CheckedDivide(exp, firstLevel()));
                    }
                    default -> {
                        return exp;
                    }
                }
            }
        }

        private AnyOperation firstLevel() throws ParserException {
            switch (token) {
                case COUNT -> {
                    getToken();
                    return new Count(firstLevel());
                }
                case CONSTANT -> {
                    return parseVariableOrConst(Token.CONSTANT);
                }
                case VARIABLE -> {
                    return parseVariableOrConst(Token.VARIABLE);
                }
                case SUBTRACT -> {
                    getToken();
                    return (!checkedOrNo ? new Negative(firstLevel()) : new CheckedNegate(firstLevel()));
                }
                case OPEN_BRACKET -> {
                    getToken();
                    AnyOperation exp = fourthLevel();
                    if (token != Token.CLOSED_BRACKET) {
                        throw new BracketedException("expected ')'");
                    }
                    getToken();
                    return exp;
                }
                case SET, CLEAR, EOF, MULTIPLY, ADD, DIVIDE ->
                        throw new IllegalArgumentException("The operation is incorrectly drafted");
                default -> throw new IllegalArgumentException("Wrong argument");
            }
        }

        private AnyOperation parseVariableOrConst(final Token token) {
            while (isLetter('x', 'z') || isDigit()) {
                constOrVariable.append(take());
            }
            getToken();
            StringBuilder res = constOrVariable;
            constOrVariable = new StringBuilder();
            if (token == Token.CONSTANT) {
                try {
                    return new Const(Integer.parseInt(res.toString()));
                } catch (NumberFormatException e) {
                    throw new OverflowException();
                }
            } else if (token == Token.VARIABLE) {
                return new Variable(res.toString());
            }
            throw new IllegalArgumentException();
        }

        private void getToken() {
            skipWhitespace();
            if (eof()) {
                token = Token.EOF;
            } else if (take('(')) {
                token = Token.OPEN_BRACKET;
            } else if (take(')')) {
                token = Token.CLOSED_BRACKET;
            } else if (take('*')) {
                token = Token.MULTIPLY;
            } else if (take('/')) {
                token = Token.DIVIDE;
            } else if (take('+')) {
                token = Token.ADD;
            } else if (take('-')) {
                parseSomethingWithMinus();
            } else if (take("count")) {
                checkError();
                token = Token.COUNT;
            } else if (take("set")) {
                checkError();
                token = Token.SET;
            } else if (take("clear")) {
                checkError();
                token = Token.CLEAR;
            } else if (isDigit()) {
                token = Token.CONSTANT;
            } else if (isLetter('x', 'z')) {
                token = Token.VARIABLE;
            } else {
                throw new IllegalArgumentException("False token");
            }
        }

        private void checkError() {
            if (isDigit() || isLetter('x', 'z')) {
                throw new IllegalArgumentException("invalid symbol next to operation");
            }
        }

        private void parseSomethingWithMinus() {
            if (token != Token.CONSTANT && token != Token.VARIABLE && token != Token.CLOSED_BRACKET && isDigit()) {
                constOrVariable.append("-");
                token = Token.CONSTANT;
            } else {
                token = Token.SUBTRACT;
            }
        }
    }
}