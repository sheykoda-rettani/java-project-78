package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    /**
     * Список валидаторов.
     */
    protected final List<Predicate<T>> validators = new ArrayList<>();

    /**
     * Проверка схемы на валидность.
     * @param value значение для проверки
     * @return результат валидации схемы
     */
    public boolean isValid(final T value) {
        for (Predicate<T> validator : validators) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }
}
