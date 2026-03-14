package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public final class StringSchema {
    private final List<StringPredicate> validators = new ArrayList<>();

    public StringSchema required() {
        validators.add((s) -> s != null && !s.isBlank());
        return this;
    }

    public StringSchema minLength(int minLength) {
        validators.add((s) -> s != null && s.length() >= minLength);
        return this;
    }

    public StringSchema contains(String substring) {
        validators.add((s) -> s != null && s.toLowerCase().contains(substring.toLowerCase()));
        return this;
    }

    public boolean isValid(String input) {
        for (StringPredicate predicate : validators) {
            if (!predicate.test(input)) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    interface StringPredicate {
        boolean test(String str);
    }
}
