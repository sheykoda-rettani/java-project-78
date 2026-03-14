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
        var validator = new Validator();
        var schema = validator.string().minLength(5);

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
        var validator = new Validator();
        var schema = validator.string().required().minLength(5).contains("hex");

        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("he"));
        assertFalse(schema.isValid("hexl"));
        assertTrue(schema.isValid("Hexlet"));
    }

    @Test
    public void testMultipleCallsOfSameMethod() {
        var validator = new Validator();
        var schema = validator.string().minLength(10).minLength(4);

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
        var validator = new Validator();
        var schema = validator.string().required().minLength(3);

        assertFalse(schema.isValid(null));
    }

    @Test
    public void testEmptyStringForValidators() {
        var validator = new Validator();
        var schema = validator.string().required().minLength(3);

        assertFalse(schema.isValid(""));
    }

    @Test
    public void testAllValidatorsPass() {
        var validator = new Validator();
        var schema = validator.string().required().minLength(7).contains("world");

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
        var validator = new Validator();
        var schema = validator.string().required().minLength(5).contains("test");

        assertFalse(schema.isValid("tst"));
        assertFalse(schema.isValid("tes"));
        assertTrue(schema.isValid("testtest"));
    }
}
