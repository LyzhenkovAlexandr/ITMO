package expression.parser;

public interface CharSource {
    boolean hasNext();

    char next();

    void savePosition();

    char getSavedPosition();

    IllegalArgumentException error(String message);
}
