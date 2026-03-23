package hexlet.code.validation;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MapValidatorTest {

    @Test
    void validationPassesForNullByDefault() {
        var validator = new Validator();
        var schema = validator.map();
        assertTrue(schema.isValid(null));
    }

    @Test
    void validationFailsForNullWhenRequired() {
        var validator = new Validator();
        var schema = validator.map().required();
        assertFalse(schema.isValid(null));
    }

    @Test
    void emptyMapIsValidByDefault() {
        var validator = new Validator();
        MapSchema schema = validator.map();
        Map<Object, Object> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void emptyMapIsValidWhenRequired() {
        var validator = new Validator();
        MapSchema schema = validator.map();
        schema.required();
        Map<Object, Object> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void sizeValidationWorksCorrectly() {
        var validator = new Validator();
        MapSchema schema = validator.map();

        final int validSize = 1;
        final int invalidSize = 2;

        Map<Object, Object> testMap = new HashMap<>();
        testMap.put("key1", "value1");

        assertTrue(schema.isValid(testMap));

        schema.sizeof(validSize);
        assertTrue(schema.isValid(testMap));

        schema.sizeof(invalidSize);
        assertFalse(schema.isValid(testMap));

        testMap.put("key2", "value2");
        assertTrue(schema.isValid(testMap));
    }

    @Test
    void shapeValidationPassesWhenAllFieldsAreCorrect() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";
        final String lastNameKey = "lastName";
        final int minLastNameLength = 2;


        Map<Object, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());
        shape.put(lastNameKey, validator.string().required().minLength(minLastNameLength));

        schema.shape(shape);

        Map<Object, Object> validData = new HashMap<>();
        validData.put(firstNameKey, "John");
        validData.put(lastNameKey, "Smith");

        assertTrue(schema.isValid(validData));
    }

    @Test
    void shapeValidationFailsWhenRequiredFieldIsNull() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";

        Map<Object, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());

        schema.shape(shape);

        Map<Object, Object> invalidData = new HashMap<>();
        invalidData.put(firstNameKey, null);

        assertFalse(schema.isValid(invalidData));
    }

    @Test
    void shapeValidationFailsWhenRequiredFieldIsMissing() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";

        Map<Object, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());

        schema.shape(shape);

        Map<Object, Object> invalidData = new HashMap<>();

        assertFalse(schema.isValid(invalidData));
    }

    @Test
    void lastShapeCallHasPriority() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";
        final String lastNameKey = "lastName";

        Map<Object, BaseSchema<?>> firstShape = new HashMap<>();
        firstShape.put(firstNameKey, validator.string().required());

        Map<Object, BaseSchema<?>> secondShape = new HashMap<>();
        secondShape.put(lastNameKey, validator.string().required());

        schema.shape(firstShape).shape(secondShape);

        Map<Object, Object> data = new HashMap<>();
        data.put(lastNameKey, "Smith");

        assertTrue(schema.isValid(data));
    }
}
