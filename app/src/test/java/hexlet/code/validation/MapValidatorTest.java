package hexlet.code.validation;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MapValidatorTest {
    /**
     * Для упрощения проверок - вынесен.
     */
    private Validator validator;

    /**
     * Для упрощения проверок - вынесена.
     */
    private MapSchema<String, String> schema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.map();
    }

    @Test
    void testNullInput() {
        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
    }

    @Test
    void testEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));

        schema.required();
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void testSizeOfOneEntry() {
        Map<String, String> oneEntryMap = new HashMap<>();
        oneEntryMap.put("key1", "value1");
        assertTrue(schema.isValid(oneEntryMap));

        schema.sizeof(1);
        assertTrue(schema.isValid(oneEntryMap));
    }

    @Test
    void testSizeOfTwoEntries() {
        Map<String, String> twoEntryMap = new HashMap<>();
        twoEntryMap.put("key1", "value1");
        twoEntryMap.put("key2", "value2");
        assertTrue(schema.isValid(twoEntryMap));

        schema.sizeof(2);
        assertTrue(schema.isValid(twoEntryMap));
    }

    @Test
    void testInvalidSize() {
        Map<String, String> invalidSizeMap = new HashMap<>();
        invalidSizeMap.put("key1", "value1");
        schema.sizeof(2);
        assertFalse(schema.isValid(invalidSizeMap));
    }

    @Test
    void testBasicShapeValidation() {
        final int lastNameLength = 2;
        StringSchema firstNameSchema = validator.string().required();
        StringSchema lastNameSchema = validator.string().required().minLength(lastNameLength);

        Map<String, BaseSchema<String>> personShapes = new HashMap<>();
        personShapes.put("firstName", firstNameSchema);
        personShapes.put("lastName", lastNameSchema);
        schema.shape(personShapes);

        Map<String, String> validHuman = new HashMap<>();
        validHuman.put("firstName", "Alice");
        validHuman.put("lastName", "Johnson");
        assertTrue(schema.isValid(validHuman));

        Map<String, String> invalidHuman1 = new HashMap<>();
        invalidHuman1.put("firstName", "Bob");
        invalidHuman1.put("lastName", "");
        assertFalse(schema.isValid(invalidHuman1));

        Map<String, String> invalidHuman2 = new HashMap<>();
        invalidHuman2.put("firstName", "Charlie");
        invalidHuman2.put("lastName", "J");
        assertFalse(schema.isValid(invalidHuman2));
    }
}
