package expression.parser;

import expression.*;
import expression.Set;

import java.util.*;

public class EParser extends BaseParser {

    private final static Map<String, Integer> mapPriority = Map.of(
            "Negative", 4,
            "/", 3,
            "*", 3,
            "+", 2,
            "-", 2,
            "set", 1,
            "clear", 1
    );

    private final Stack<AnyOperation> stackExpr = new Stack<>();
    private final Stack<String> stackOper = new Stack<>();

    public EParser(CharSource sourse) {
        super(sourse);
    }

    public AnyOperation parse() {
        parseOneMayBeOpeningBracket();
        while (sourse.hasNext() || look(')')) {
            String op = parseOperationSign();
            if (op.equals(")")) {
                while (!stackOper.peek().equals("(")) {
                    makeCalculation();
                }
                stackOper.pop();
            } else {
                while (!stackOper.isEmpty() && !stackOper.peek().equals("(") && mapPriority.get(stackOper.peek()) >= mapPriority.get(op)) {
                    makeCalculation();
                }
                if (stackOper.isEmpty() || stackOper.peek().equals("(") || mapPriority.get(stackOper.peek()) < mapPriority.get(op)) {
                    stackOper.push(op);
                    skipWhiteSpace();
                    while (take('(')) {
                        stackOper.push("(");
                    }
                    parseOneMayBeOpeningBracket();
                }
            }
            skipWhiteSpace();
        }

        while (!stackOper.isEmpty()) {
            makeCalculation();
        }
        return stackExpr.pop();
    }

    private void makeCalculation() {
        String lastOper = stackOper.pop();
        if (lastOper.equals("Negative")) {
            stackExpr.push(new Negative(stackExpr.pop()));
        } else {
            AnyOperation ex2 = stackExpr.pop();
            AnyOperation ex1 = stackExpr.pop();
            stackExpr.push(prepareExpression(lastOper, ex1, ex2));
        }
    }

    private void parseOneMayBeOpeningBracket() {
        skipWhiteSpace();
        if (!look('(')) {
            stackExpr.push(parseOne());
        }
    }

    // Один операнд: Negative, константа, переменная или выражение в скобках
    private AnyOperation parseOne() {
        skipWhiteSpace();
        if (look('-')) {
            return parseSomethingWithMinus();
        } else {
            return parseConstOrVariable();  // константа (неотрицательная!) или переменная
        }
    }

    // что-то, начинающееся с минуса.
    private AnyOperation parseSomethingWithMinus() {
        take('-');
        if (isDigit(look())) {
            return parseConst(true);
        }
        skipWhiteSpace();
        stackOper.push("Negative");
        while (look('(')) {
            skipWhiteSpace();
            stackOper.push(parseOperationSign());
            skipWhiteSpace();
        }
        return parseOne();
    }

    private String parseOperationSign() {
        skipWhiteSpace();
        if (look('+') || look('-') || look('*') || look('/') || look('(') || look(')')) {
            return String.valueOf(take());
        } else if (take('s')) {
            expect("et");
            return "set";
        } else if (take('c')) {
            expect("lear");
            return "clear";
        } else {
            throw sourse.error("Unsupported operation sign: " + look());
        }
    }

    private AnyOperation parseConstOrVariable() {
        skipWhiteSpace();
        if (isDigit(look())) {
            return parseConst(false);
        } else if (isLetter(look())) {
            return parseVariable();
        } else {
            throw error("Unsupported input: " + (int) look());
        }
    }

    private AnyOperation parseConst(boolean minus) {
        final StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append('-');
        }
        while (isDigit(look())) {
            sb.append(take());
        }
        int value = Integer.parseInt(sb.toString());
        return new Const(value);
    }

    private AnyOperation parseVariable() {
        return new Variable(String.valueOf(take()));
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(look())) {
            take();
        }
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private static AnyOperation prepareExpression(String s, AnyOperation ex1, AnyOperation ex2) {
        return switch (s) {
            case "+" -> new Add(ex1, ex2);
            case "-" -> new Subtract(ex1, ex2);
            case "*" -> new Multiply(ex1, ex2);
            case "/" -> new Divide(ex1, ex2);
            case "set" -> new Set(ex1, ex2);
            case "clear" -> new Clear(ex1, ex2);
            default -> throw new IllegalArgumentException("Unknown operation");
        };
    }
}