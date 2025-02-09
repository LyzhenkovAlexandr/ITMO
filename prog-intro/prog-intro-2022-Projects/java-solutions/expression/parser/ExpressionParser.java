package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
        EParser parser = new EParser(new StringCharSource(expression));
        return parser.parse();
    }

}
