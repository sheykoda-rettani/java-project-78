package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    /**
     * Хранит схемы для каждого поля.
     */
    private final Map<Object, BaseSchema<?>> shapeSchemas = new HashMap<>();

    public MapSchema required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(final int expectedSize) {
        addValidator("sizeOf", map -> map != null && map.size() == expectedSize);
        return this;
    }

    public MapSchema shape(final Map<Object, BaseSchema<?>> keySchemas) {
        if (keySchemas != null && !keySchemas.isEmpty()) {
            this.shapeSchemas.clear();
            this.shapeSchemas.putAll(keySchemas);
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(final Map<K, V> value) {
        if (!super.isValid(value)) {
            return false;
        }

        for (Map.Entry<Object, BaseSchema<?>> entry : shapeSchemas.entrySet()) {
            final Object key = entry.getKey();
            final BaseSchema<?> schema = entry.getValue();

            final var fieldValue = value.get(key);

            if (!((BaseSchema<Object>) schema).isValid(fieldValue)) {
                return false;
            }
        }

        return true;
    }
}
