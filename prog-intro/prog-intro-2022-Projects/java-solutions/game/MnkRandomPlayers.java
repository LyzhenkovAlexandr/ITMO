package game;

import java.util.Random;


//класс определяющий действия игрока ходящего рандомно
public class MnkRandomPlayers implements Players {
    private final Random random;

    public MnkRandomPlayers(final Random random) {
        this.random = random;
    }

    public MnkRandomPlayers() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final CellMNK cell) {
        while (true) {
            int r = random.nextInt(position.getM());
            int c = random.nextInt(position.getN());
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}

