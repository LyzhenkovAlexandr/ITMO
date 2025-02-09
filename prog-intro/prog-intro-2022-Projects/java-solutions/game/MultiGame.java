package game;

public class MultiGame {
    private final int[] pos;
    private final Players[] players;
    private final MNKScore score;
    final boolean log = true;

    public MultiGame(int[] pos, Players[] players, MNKScore score) {
        this.pos = pos;
        this.players = players;
        this.score = score;
    }

    public void GameStart() {
        MNKBoard mnkBoard = new MNKBoard(pos[0], pos[1], pos[2]) {
        };
        int c = 3;
//инициализируем 3 запусков для непрерывного тестирования
        while (c-- != 0) {
            for (int i = 0; i < players.length; i++) {
                for (int j = i + 1; j < players.length; j++) {
                    //инициализируем игру
                    final GameMNK game = new GameMNK(players[i], players[j], log);
                    //запускаем игру на поле м н
                    int result = game.play(mnkBoard);
                    score.record(i, j, result);
                    if (score.getWin(i) == 2) {
                        c = 0;
                    }
                    if (score.getWin(j) == 2) {
                        c = 0;
                    }
                    System.out.println("Выводим текущую таблицу игры:");
                    System.out.println(score);
                    mnkBoard.clearBoard();
                }
            }
        }
        System.out.println();
        System.out.println("Выводим таблицу результатов по  запускам:");
        System.out.println(score);
    }
}
