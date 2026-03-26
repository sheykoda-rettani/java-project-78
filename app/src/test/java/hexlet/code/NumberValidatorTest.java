package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class NumberValidatorTest {
    @Test
    void testDefaultIsValid() {
        final int validInt = 5;
        Validator validator = new Validator();
        NumberSchema schema = validator.number();
        assertTrue(schema.isValid(validInt));
        assertTrue(schema.isValid(null));
    }

    @Test
    void testRequired() {
        final int validInt = 10;
        Validator validator = new Validator();
        NumberSchema schema = validator.number().required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(validInt));
    }

    @Test
    void testPositive() {
        final int negative = -1;
        final int zero = 0;
        final int positive = 66;
        Validator validator = new Validator();
        NumberSchema schema = validator.number().positive();
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(negative));
        assertFalse(schema.isValid(zero));
        assertTrue(schema.isValid(positive));
    }

    @Test
    void testRange() {
        final int lowerBound = 5;
        final int upperBound = 10;
        final int inBound = 6;
        final int higher = 11;
        final int lower = 4;
        Validator validator = new Validator();
        NumberSchema schema = validator.number().range(lowerBound, upperBound);
        assertTrue(schema.isValid(lowerBound));
        assertTrue(schema.isValid(upperBound));
        assertTrue(schema.isValid(inBound));
        assertFalse(schema.isValid(lower));
        assertFalse(schema.isValid(higher));
    }

    @Test
    void testCombined() {
        final int lowerBound = 5;
        final int upperBound = 10;
        Validator validator = new Validator();
        NumberSchema schema = validator.number().required().positive().range(lowerBound, upperBound);
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(lowerBound));
        assertTrue(schema.isValid(upperBound));
        assertTrue(schema.isValid(lowerBound + ((upperBound - lowerBound) / 2)));
        assertFalse(schema.isValid(lowerBound - 1));
    }
}
