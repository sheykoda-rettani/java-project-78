package hexlet.code;

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
        Map<String, String> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void emptyMapIsValidWhenRequired() {
        var validator = new Validator();
        MapSchema schema = validator.map();
        schema.required();
        Map<String, String> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void sizeValidationWorksCorrectly() {
        var validator = new Validator();
        MapSchema schema = validator.map();

        final int validSize = 1;
        final int invalidSize = 2;

        Map<String, String> testMap = new HashMap<>();
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


        Map<String, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());
        shape.put(lastNameKey, validator.string().required().minLength(minLastNameLength));

        schema.shape(shape);

        Map<String, String> validData = new HashMap<>();
        validData.put(firstNameKey, "John");
        validData.put(lastNameKey, "Smith");

        assertTrue(schema.isValid(validData));
    }

    @Test
    void shapeValidationFailsWhenRequiredFieldIsNull() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";

        Map<String, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());

        schema.shape(shape);

        Map<String, String> invalidData = new HashMap<>();
        invalidData.put(firstNameKey, null);

        assertFalse(schema.isValid(invalidData));
    }

    @Test
    void shapeValidationFailsWhenRequiredFieldIsMissing() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";

        Map<String, BaseSchema<?>> shape = new HashMap<>();
        shape.put(firstNameKey, validator.string().required());

        schema.shape(shape);

        Map<String, String> invalidData = new HashMap<>();

        assertFalse(schema.isValid(invalidData));
    }

    @Test
    void lastShapeCallHasPriority() {
        var validator = new Validator();
        var schema = validator.map();

        final String firstNameKey = "firstName";
        final String lastNameKey = "lastName";

        Map<String, BaseSchema<?>> firstShape = new HashMap<>();
        firstShape.put(firstNameKey, validator.string().required());

        Map<String, BaseSchema<?>> secondShape = new HashMap<>();
        secondShape.put(lastNameKey, validator.string().required());

        schema.shape(firstShape).shape(secondShape);

        Map<String, String> data = new HashMap<>();
        data.put(lastNameKey, "Smith");

        assertTrue(schema.isValid(data));
    }

    @Test
    void partialAndMixedShapeWorksAsExpected() {
        var validator = new Validator();
        var schema = validator.map();
        final int minLength = 2;
        final int minAge = 18;
        final int maxAge = 200;

        Map<String, BaseSchema<?>> partialShapes = new HashMap<>();
        partialShapes.put("surname", validator.string().required().minLength(minLength));
        partialShapes.put("age", validator.number().range(minAge, maxAge));
        schema.shape(partialShapes);

        var legalPerson = Map.of("surname", "Smith", "name", "John", "age", minAge);
        assertTrue(schema.isValid(legalPerson));
        var illegalSurname = Map.of("surname", "A", "name", "John", "age", minAge);
        assertFalse(schema.isValid(illegalSurname));
        var illegalAge = Map.of("surname", "Smith", "name", "John", "age", minAge - 1);
        assertFalse(schema.isValid(illegalAge));
    }
}
