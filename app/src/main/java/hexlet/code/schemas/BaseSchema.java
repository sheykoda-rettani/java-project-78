package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

abstract class BaseSchema<T> {
    /**
     * Список валидаторов.
     */
    protected final List<Predicate<T>> validators = new ArrayList<>();

    public boolean isValid(final T value) {
        for (Predicate<T> validator : validators) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }
}
