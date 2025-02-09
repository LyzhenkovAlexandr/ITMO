package game;

import java.io.PrintStream;
import java.util.Scanner;


//класс для обработки ходов живого игрока
public class HumanPlay implements Players {

    private final PrintStream out;
    private final Scanner in;

    public HumanPlay(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    //инициализируем ввод вывод
    public HumanPlay() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final CellMNK cell) {
        while (true) {
            int[] pos = new int[2];
            out.println("Позиция");
            out.println(position);
            out.println(cell + " ход");
            out.println("Введите номер строки и столбца через пробел");

            int idx = 0;
            while (idx < 2) {
                pos[idx++] = nextInt();
            }

            final Move move = new Move(pos[0], pos[1], cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getCol();
            out.println("Ход " + move + " не действительный");
        }
    }

    private int nextInt() {
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
