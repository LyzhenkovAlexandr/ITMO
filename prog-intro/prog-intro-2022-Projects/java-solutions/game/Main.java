package game;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = new PrintStream(System.out);
        ListernKey listernKey = new ListernKey(out, in);

        int[] pos = new int[3];
        final boolean log = true;
        //задаем массив для рандомного выбора пары игроков
        final Players[] players = {new MnkRandomPlayers(), new MnkRandomPlayers(), new LinealPlayers(), new LinealPlayers()};
        final MNKScore score = new MNKScore(players.length, log);

//запрашиваем ввод параметров для запуска м н к
        int idx = 0;
        while (idx < 3) {
            pos[idx++] = listernKey.nextInt();
        }

        new MultiGame(pos, players, score).GameStart();
        System.out.println("Игры завершины");
    }
}
