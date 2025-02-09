package markup;

import java.util.List;


public abstract class MarkAbstract implements Mark {
    private final List<Mark> array;

    public MarkAbstract(final List<Mark> array) {
        this.array = array;
    }

    @Override
    public final void toMarkdown(final StringBuilder s) {
        final String ch = getMarkDown();
        s.append(ch);
        for (Mark el : array) {
            el.toMarkdown(s);
        }
        s.append(ch);
    }

    @Override
    public void toTex(final StringBuilder s) {
        final String tag = getTex();
        s.append('\\');
        s.append(tag);
        s.append('{');
        for (Mark el : array) {
            el.toTex(s);
        }
        s.append('}');
    }

    protected abstract String getMarkDown();

    protected abstract String getTex();
}