package markup;

import java.util.List;

public final class Strikeout extends MarkAbstract {
    public Strikeout(final List<Mark> array) {
        super(array);
    }

    @Override
    protected String getMarkDown() {
        return "~";
    }

    @Override
    protected String getTex() {
        return "textst";
    }
}