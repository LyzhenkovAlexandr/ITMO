package expression.parser;

public interface CharSource {
    boolean hasNext();
    char next();
    void saveChar();
    char returnChar();
    IllegalArgumentException error(String message);
}
