package game;


public class GameMNK {
    private final boolean log;
    private final Players player1;
    private final Players player2;

    public GameMNK(Players player1, Players player2, final boolean log) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(final BoardMNK boardMNK) {
        while (true) {
            while (true) {
                final int result1 = move(boardMNK, player1, 1);
                if (result1 != -1 && result1 != -2) {
                    return result1;
                }
                log();
                log("Текущий состояние игры: " + result1);
                if (result1 != -2) {
                    break;
                }
            }
            while (true) {
                final int result2 = move(boardMNK, player2, 2);
                if (result2 != -1 && result2 != -2) {
                    return result2;
                }
                log();
                log("Текущее состояние игры: " + result2);
                if (result2 != -2) {
                    break;
                }
            }
        }
    }

    private int move(final BoardMNK board, final Players player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final ResultGame result = board.makeMove(move);
        log("Игрок " + no + " шаг: " + move);
        log("Позиция:\n" + board);
        if (result == ResultGame.WIN) {
            log("Игрок " + no + " победил");
            return no;
        } else if (result == ResultGame.LOSE) {
            log("Игрок " + no + " проиграл");
            return 3 - no;
        } else if (result == ResultGame.DRAW) {
            log("Ничья");
            return 0;
        } else if (result == ResultGame.BONUS) {
            log("Бонусный ход");
            return -2;
        } else {
            return -1;
        }
    }


    //выводим запись в лог
    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }

    private void log() {
        if (log) {
            System.out.println();
        }
    }
}
