package expression;

import expression.parser.MathLogic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MathLogicExpression expr = MathLogic.parse(scanner.nextLine());
        System.out.println(expr.toStringFormat());
    }
}
