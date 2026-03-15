package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        validators.add(Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        validators.add(n -> n != null && n > 0);
        return this;
    }

    public NumberSchema range(final Integer from, final Integer to) {
        validators.add(n -> n != null && n >= from && n <= to);
        return this;
    }
}
