package expression.exceptions;


import expression.TripleExpression;
import expression.parser.StringCharSource;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) throws ParserException {
        expression.parser.ExpressionParser.EParser parser = new expression.parser.ExpressionParser.EParser(new StringCharSource(expression));
        return parser.parse(true);
    }
}