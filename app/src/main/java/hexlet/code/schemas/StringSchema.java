package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        addValidator("required", s -> s != null && !s.isBlank());
        return this;
    }

    public StringSchema minLength(final int minLength) {
        addValidator("minLength", s -> s != null && s.length() >= minLength);
        return this;
    }

    public StringSchema contains(final String substring) {
        addValidator("contains", s -> s != null && s.toLowerCase().contains(substring.toLowerCase()));
        return this;
    }
}
