package game;

import java.io.PrintStream;
import java.util.Scanner;

public class ListernKey {
    private final PrintStream out;
    private final Scanner in;

    public ListernKey(PrintStream out, Scanner in) {
        this.out = out;
        this.in = in;
    }

    public int nextInt() {
        while (true) {
            if (!in.hasNext()) {
                out.println("Патовое положение");
                throw new IllegalStateException("Завершаем игру");
            }
            if (in.hasNextInt()) {
                return in.nextInt();
            }
            in.next();
        }
    }
}
