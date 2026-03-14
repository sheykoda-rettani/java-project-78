package hexlet.code.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ValidatorTest {

    @Test
    public void testRequiredValidation() {
        var validator = new Validator();
        var schema = validator.string().required();

        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("nonEmpty"));
    }

    @Test
    public void testMinLengthValidation() {
        final int minLength = 5;
        var validator = new Validator();
        var schema = validator.string().minLength(minLength);

        assertFalse(schema.isValid("abcd"));
        assertTrue(schema.isValid("abcdef"));
    }

    @Test
    public void testContainsValidation() {
        var validator = new Validator();
        var schema = validator.string().contains("abc");

        assertFalse(schema.isValid("xyz"));
        assertTrue(schema.isValid("abccba"));
    }

    @Test
    public void testCombinedValidations() {
        final int minLength = 6;
        var validator = new Validator();
        var schema = validator.string().required().minLength(minLength).contains("hex");

        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("he"));
        assertFalse(schema.isValid("hexl"));
        assertTrue(schema.isValid("Hexlet"));
    }

    @Test
    public void testMultipleCallsOfSameMethod() {
        final int minLength1 = 10;
        final int minLength2 = 4;
        var validator = new Validator();
        var schema = validator.string().minLength(minLength1).minLength(minLength2);

        assertFalse(schema.isValid("shor"));
        assertTrue(schema.isValid("thisIsLongEnough"));
    }

    @Test
    public void testNoValidation() {
        var validator = new Validator();
        var schema = validator.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("anything"));
    }

    @Test
    public void testNullInputForValidators() {
        final int minLength = 3;
        var validator = new Validator();
        var schema = validator.string().required().minLength(minLength);

        assertFalse(schema.isValid(null));
    }

    @Test
    public void testEmptyStringForValidators() {
        final int minLength = 3;
        var validator = new Validator();
        var schema = validator.string().required().minLength(minLength);

        assertFalse(schema.isValid(""));
    }

    @Test
    public void testAllValidatorsPass() {
        final int minLength = 7;
        var validator = new Validator();
        var schema = validator.string().required().minLength(minLength).contains("world");

        assertTrue(schema.isValid("Hello world!"));
    }

    @Test
    public void testInvalidStringForContains() {
        var validator = new Validator();
        var schema = validator.string().contains("fox");

        assertFalse(schema.isValid("dog"));
    }

    @Test
    public void testNullForContains() {
        var validator = new Validator();
        var schema = validator.string().contains("fox");

        assertFalse(schema.isValid(null));
    }

    @Test
    public void testSingleCharacterStrings() {
        var validator = new Validator();
        var schema = validator.string().minLength(1);

        assertTrue(schema.isValid("a"));
        assertFalse(schema.isValid(""));
    }

    @Test
    public void testMultipleConstraintsOrder() {
        final int minLength = 5;
        var validator = new Validator();
        var schema = validator.string().required().minLength(minLength).contains("test");

        assertFalse(schema.isValid("tst"));
        assertFalse(schema.isValid("tes"));
        assertTrue(schema.isValid("testtest"));
    }
}
