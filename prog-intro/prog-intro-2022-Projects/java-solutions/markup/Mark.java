package markup;

public interface Mark {
    void toMarkdown(final StringBuilder s);

    void toTex(final StringBuilder s);
}