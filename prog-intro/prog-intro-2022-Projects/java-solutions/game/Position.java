package game;


//интерфейс для определения позиции
public interface Position {
    int getM();

    int getN();

    boolean isValid(Move move);

    CellMNK getCell(int r, int c);
}
