package expression.generic;

import expression.TypesWithOperations.*;

import java.util.Map;
import java.util.Objects;

public class GenericTabulator implements Tabulator {
    public static final Map<String, OperationWithType<? extends Number>> TYPES = Map.of(
            "u", new UncheckedInteger(),
            "d", new UncheckedDouble(),
            "i", new CheckedInteger(),
            "bi", new UncheckedBigInteger(),
            "f", new UncheckedFloat(),
            "s", new UncheckedShort()
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        OperationWithType<? extends Number> type = TYPES.get(mode);
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("Unknown type: " + mode);
        }
        return fill(type, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T extends Number> Object[][][] fill(OperationWithType<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] R = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        AnyOperation<T> expr = new ExpressionParser<T>().parse(expression, type);

        for (int i = 0; i < x2 - x1 + 1; i++) {
            T one = type.valueOf(String.valueOf(i + x1));
            for (int j = 0; j < y2 - y1 + 1; j++) {
                T two = type.valueOf(String.valueOf(j + y1));
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    T three = type.valueOf(String.valueOf(k + z1));
                    try {
                        R[i][j][k] = expr.evaluate(one, two, three);
                    } catch (RuntimeException e) {

                    }
                }
            }
        }
        return R;
    }
}