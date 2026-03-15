package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    /**
     * Ограничение размера мапы.
     */
    private Predicate<Map<K, V>> sizeConstraint = m -> true;

    public MapSchema<K, V> required() {
        validators.add(Objects::nonNull);
        return this;
    }

    public MapSchema<K, V> sizeof(final int expectedSize) {
        sizeConstraint = map -> map != null && map.size() == expectedSize;
        validators.add(sizeConstraint);
        return this;
    }

    @Override
    public boolean isValid(final Map<K, V> input) {
        return super.isValid(input) && sizeConstraint.test(input);
    }
}
