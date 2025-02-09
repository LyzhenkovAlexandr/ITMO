package markup;

import java.util.List;

public final class Emphasis extends MarkAbstract {
    public Emphasis(final List<Mark> array) {
        super(array);
    }

    @Override
    protected String getMarkDown() {
        return "*";
    }

    @Override
    protected String getTex() {
        return "emph";
    }
}