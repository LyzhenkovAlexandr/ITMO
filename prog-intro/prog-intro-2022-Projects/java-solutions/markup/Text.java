package markup;

public final class Text implements Mark {
    private final String text;

    public Text(final String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(final StringBuilder s) {
        s.append(text);
    }

    @Override
    public void toTex(final StringBuilder s) {
        s.append(text);
    }
}