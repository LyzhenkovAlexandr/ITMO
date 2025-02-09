package expression.generic;


import expression.exceptions.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringCharSource;
import expression.parser.Token;
import expression.TypesWithOperations.OperationWithType;

import java.lang.IllegalArgumentException;

public class ExpressionParser<T extends Number> {

    public AnyOperation<T> parse(String expression, OperationWithType<T> type) {
        try {
            EParser<T> parser = new EParser<>(new StringCharSource(expression), type);
            return parser.parse();
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static class EParser<T extends Number> extends BaseParser {
        private Token token;
        private StringBuilder constOrVariable = new StringBuilder();
        private final OperationWithType<T> type;


        public EParser(CharSource sourse, OperationWithType<T> type) {
            super(sourse);
            this.type = type;
        }

        public AnyOperation<T> parse() throws ParserException {
            getToken();
            AnyOperation<T> exp = thirdLevel();
            if (token != Token.EOF) {
                throw new IllegalArgumentException("Unexpected char");
            }
            return exp;
        }

        private AnyOperation<T> thirdLevel() throws ParserException {
            AnyOperation<T> exp = secondLevel();
            while (true) {
                switch (token) {
                    case ADD -> {
                        getToken();
                        exp = new Add<>(exp, secondLevel(), type);
                    }
                    case SUBTRACT -> {
                        getToken();
                        exp = new Subtract<>(exp, secondLevel(), type);
                    }
                    default -> {
                        return exp;
                    }
                }
            }
        }

        private AnyOperation<T> secondLevel() throws ParserException {
            AnyOperation<T> exp = firstLevel();
            while (true) {
                switch (token) {
                    case MULTIPLY -> {
                        getToken();
                        exp = new Multiply<>(exp, firstLevel(), type);
                    }
                    case DIVIDE -> {
                        getToken();
                        exp = new Divide<>(exp, firstLevel(), type);
                    }
                    default -> {
                        return exp;
                    }
                }
            }
        }

        private AnyOperation<T> firstLevel() throws ParserException {
            switch (token) {
                case COUNT -> {
                    getToken();
                    return new Count<>(firstLevel(), type);
                }
                case CONSTANT -> {
                    return parseVariableOrConst(Token.CONSTANT);
                }
                case VARIABLE -> {
                    return parseVariableOrConst(Token.VARIABLE);
                }
                case SUBTRACT -> {
                    getToken();
                    return new Negative<>(firstLevel(), type);
                }
                case OPEN_BRACKET -> {
                    getToken();
                    AnyOperation<T> exp = thirdLevel();
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

        private AnyOperation<T> parseVariableOrConst(final Token token) {
            while (isLetter('x', 'z') || isDigit()) {
                constOrVariable.append(take());
            }
            getToken();
            StringBuilder res = constOrVariable;
            constOrVariable = new StringBuilder();
            if (token == Token.CONSTANT) {
                try {
                    return new Const<>(type.valueOf(res.toString()));
                } catch (NumberFormatException e) {
                    throw new OverflowException();
                }
            } else if (token == Token.VARIABLE) {
                return new Variable<>(res.toString());
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