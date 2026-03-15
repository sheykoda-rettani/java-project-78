package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        validators.add(s -> s != null && !s.isBlank());
        return this;
    }

    public StringSchema minLength(final int minLength) {
        validators.add(s -> s != null && s.length() >= minLength);
        return this;
    }

    public StringSchema contains(final String substring) {
        validators.add(s -> s != null && s.toLowerCase().contains(substring.toLowerCase()));
        return this;
    }
}
