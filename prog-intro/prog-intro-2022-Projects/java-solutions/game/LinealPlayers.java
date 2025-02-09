package game;

//класс определяющий действия бота с последовательными шагами

public class LinealPlayers implements Players {
    @Override
    public Move move(final Position position, final CellMNK cell) {

        for (int r = 0; r < position.getM(); r++) {
            for (int c = 0; c < position.getN(); c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("Ходы отсутствуют");
    }
}
