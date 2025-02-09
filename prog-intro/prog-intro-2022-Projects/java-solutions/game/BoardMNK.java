package game;

//интерфейс для доски
public interface BoardMNK {
    Position getPosition();

    CellMNK getCell();

    ResultGame makeMove(Move move);
}
