package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    /**
     * Карта валидаторов, где ключ - маркер типа правила.
     */
    private final Map<String, Predicate<T>> validators = new LinkedHashMap<>();

    /**
     * Проверка схемы на валидность.
     * @param value значение для проверки
     * @return результат валидации схемы
     */
    public boolean isValid(final T value) {
        if (!validators.isEmpty()) {
            for (Predicate<T> validator : validators.values()) {
                if (!validator.test(value)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Защищенный метод для добавления/обновления валидатора в карте.
     * Это позволяет дочерним классам управлять правилами единообразно.
     * @param ruleName Имя правила (ключ)
     * @param predicate Предикат-валидатор
     */
    protected void addValidator(final String ruleName, final Predicate<T> predicate) {
        Objects.requireNonNull(ruleName);
        Objects.requireNonNull(predicate);
        validators.put(ruleName, predicate);
    }
}
