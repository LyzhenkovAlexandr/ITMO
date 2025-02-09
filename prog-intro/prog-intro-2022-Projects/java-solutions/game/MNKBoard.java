package game;

import java.util.Arrays;
import java.util.Map;


public class MNKBoard implements BoardMNK, Position {
    private static final Map<CellMNK, Character> SYMBOLS = Map.of(
            CellMNK.X, 'X',
            CellMNK.O, 'O',
            CellMNK.E, '.'
    );

    private final CellMNK[][] cells;
    private final Position proxy;
    private CellMNK turn;
    private final int k;
    private final int m;
    private final int n;
    private long empty;

    public MNKBoard(int m, int n, int k) {
        //ограничения игры
        if (m < 1 || n < 1 || (m < k && n < k)) {
            throw new IllegalArgumentException("С заданными параметрами игра не валидна");
        }

        this.cells = new CellMNK[m][n];
        this.k = k;
        this.m = m;
        this.n = n;
        for (CellMNK[] row : cells) {
            Arrays.fill(row, CellMNK.E);
        }
        empty = (long) m * n;

        turn = CellMNK.X;
        proxy = new ProxyFaerbool(this);
    }

    @Override
    public Position getPosition() {
        return proxy;
    }

    @Override
    public CellMNK getCell() {
        return turn;
    }

    public void clearBoard() {
        for (CellMNK[] row : this.cells) {
            Arrays.fill(row, CellMNK.E);
        }
        empty = (long) m * n;
    }

    //проверка валидности хода
    @Override
    public ResultGame makeMove(final Move move) {
        if (!isValid(move)) {
            return ResultGame.LOSE;
        }

        cells[move.getRow()][move.getCol()] = move.getCellMNK();
        empty--;

        return checkResult(move);
    }


    //проверка результата холда
    private ResultGame checkResult(final Move move) {
        int r = move.getRow();
        int c = move.getCol();
        CellMNK curTurn = move.getCellMNK();

        final int[][] vectors = {
                {1, 0}, {1, 1}, {0, 1}, {-1, 1}
        };

        for (int[] vector : vectors) {
            int cells = 1;
            int vx = vector[0];
            int vy = vector[1];
            for (int x = r + vx, y = c + vy; checkCell(x, y, curTurn); x += vx, y += vy) {
                cells++;
            }
            for (int x = r - vx, y = c - vy; checkCell(x, y, curTurn); x -= vx, y -= vy) {
                cells++;
            }
            if (cells >= k) {
                return ResultGame.WIN;
            }
            if (cells >= 5) {
                return ResultGame.BONUS;
            }
        }

        if (empty == 0) {
            return ResultGame.DRAW;
        }

        turn = turn == CellMNK.X ? CellMNK.O : CellMNK.X;
        return ResultGame.UNKNOWN;
    }

    private boolean checkCell(final int r, final int c, final CellMNK cell) {
        return 0 <= r && r < getM() && 0 <= c && c < getN() && cells[r][c] == cell;
    }

    @Override
    public boolean isValid(final Move move) {
        return checkCell(move.getRow(), move.getCol(), CellMNK.E) && move.getCellMNK() == getCell();
    }

    @Override
    public int getM() {
        return cells.length;
    }

    @Override
    public int getN() {
        return cells[0].length;
    }

    @Override
    public CellMNK getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < getN(); i++) {
            sb.append(" ");
            sb.append(i);
        }
        for (int r = 0; r < getM(); r++) {
            sb.append("\n");
            sb.append(r);
            sb.append(" ");
            for (int c = 0; c < getN(); c++) {
                sb.append(" ");
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
