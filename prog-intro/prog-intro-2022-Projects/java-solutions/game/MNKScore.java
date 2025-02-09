package game;

public class MNKScore {
    private final int[][] score;
    private final boolean log;

    public MNKScore(final int c, final boolean log) {
        score = new int[c][c];
        this.log = log;
    }

    //класс для записи результатов игры
    public void record(final int i, final int j, final int result) {
        log("Players " + (i + 1) + " and " + (j + 1) + ", result: " + result);
        switch (result) {
            case 0 -> {
                score[i][j] += 1;
                score[j][i] += 1;
            }
            case 1 -> score[i][j] += 3;
            case 2 -> score[j][i] += 3;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }

    public int getWin(final int wings) {
        int winUser = 0;
        for (int[] ints : score) {
            for (int j = 0; j < score.length; j++) {
                if (wings == ints[j]) {
                    winUser++;
                }
            }
        }
        return winUser;
    }

    @Override
    public String toString() {
        StringBuilder resultTable = new StringBuilder(" ");
        for (int i = 0; i < score.length; i++) {
            resultTable.append(" ");
            resultTable.append(i + 1);
        }
        for (int i = 0; i < score.length; i++) {
            resultTable.append("\n");
            resultTable.append(i + 1);
            for (int j = 0; j < score.length; j++) {
                resultTable.append(" ");
                if (i != j) {
                    resultTable.append(score[i][j]);
                } else {
                    resultTable.append("-");
                }
            }
        }
        return resultTable.toString();
    }
}
