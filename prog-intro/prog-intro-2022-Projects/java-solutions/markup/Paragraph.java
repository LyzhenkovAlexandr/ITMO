package markup;

import java.util.List;

public class Paragraph implements Mark {
    private final List<Mark> array;

    public Paragraph(List<Mark> array) {
        this.array = array;
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        for (Mark el : array) {
            el.toMarkdown(s);
        }
    }

    @Override
    public void toTex(StringBuilder s) {
        for (Mark el : array) {
            el.toTex(s);
        }
    }
}