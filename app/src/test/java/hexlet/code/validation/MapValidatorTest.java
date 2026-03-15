package hexlet.code.validation;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MapValidatorTest {
    /**
     * Для упрощения проверок - вынесена.
     */
    private MapSchema<String, String> schema;

    @BeforeEach
    void setUp() {
        Validator validator = new Validator();
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
}
