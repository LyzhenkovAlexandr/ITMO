package markup;

import java.util.List;

public final class Strong extends MarkAbstract {
    public Strong(final List<Mark> array) {
        super(array);
    }

    @Override
    protected String getMarkDown() {
        return "__";
    }

    @Override
    protected String getTex() {
        return "textbf";
    }
}