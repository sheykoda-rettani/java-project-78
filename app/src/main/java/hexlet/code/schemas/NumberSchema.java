package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidator("positive", n -> n != null && n > 0);
        return this;
    }

    public NumberSchema range(final Integer from, final Integer to) {
        addValidator("range", n -> n != null && n >= from && n <= to);
        return this;
    }
}
