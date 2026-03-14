package hexlet.code.validation;

import hexlet.code.schemas.StringSchema;

public final class Validator {
    public StringSchema string() {
        return new StringSchema();
    }
}
