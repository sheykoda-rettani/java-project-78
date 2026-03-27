package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    public MapSchema required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(final int expectedSize) {
        addValidator("sizeOf", map -> map != null && map.size() == expectedSize);
        return this;
    }

    public MapSchema shape(final Map<Object, BaseSchema<?>> keySchemas) {
        addValidator("shape", shapePredicate(keySchemas));
        return this;
    }

    @SuppressWarnings("unchecked")
    private Predicate<Map<K, V>> shapePredicate(final Map<Object, BaseSchema<?>> keySchemas) {
        return map -> {
            if (map == null) {
                return false;
            }
            if (keySchemas != null && !keySchemas.isEmpty()) {
                for (Map.Entry<Object, BaseSchema<?>> entry : keySchemas.entrySet()) {
                    final Object key = entry.getKey();
                    final BaseSchema<?> schema = entry.getValue();

                    final var fieldValue = map.get(key);

                    if (!((BaseSchema<Object>) schema).isValid(fieldValue)) {
                        return false;
                    }
                }
            }
            return true;
        };
    }
}
