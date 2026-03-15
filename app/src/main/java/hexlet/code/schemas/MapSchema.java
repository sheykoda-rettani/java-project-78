package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    /**
     * Хранит схемы для каждого поля.
     */
    private final Map<K, BaseSchema<V>> shapes = new HashMap<>();

    public MapSchema<K, V> required() {
        validators.add(Objects::nonNull);
        return this;
    }

    public MapSchema<K, V> sizeof(final int expectedSize) {
        validators.add(map -> map != null && map.size() == expectedSize);
        return this;
    }

    public MapSchema<K, V> shape(final Map<K, ? extends BaseSchema<V>> keySchemas) {
        shapes.putAll(keySchemas);
        return this;
    }

    @Override
    public boolean isValid(final Map<K, V> input) {
        if (!super.isValid(input)) {
            return false;
        }

        if (input != null) {
            for (Map.Entry<K, V> entry : input.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                BaseSchema<V> schemaForKey = shapes.get(key);
                if (schemaForKey != null && !schemaForKey.isValid(value)) {
                    return false;
                }
            }
        }

        return true;
    }
}
