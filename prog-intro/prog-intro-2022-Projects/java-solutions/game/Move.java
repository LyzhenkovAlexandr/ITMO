package game;

public class Move {
    private final int row;
    private final int col;
    private final CellMNK cellMNK;

    //класс обработки шага
    public Move(int row, int col, CellMNK cellMNK) {
        this.row = row;
        this.col = col;
        this.cellMNK = cellMNK;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellMNK getCellMNK() {
        return cellMNK;
    }

    @Override
    public String toString() {
        return "строка=" + row + ", столбец=" + col + ", значение=" + cellMNK;
    }
}
