package hexlet.code.validation;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class StringValidatorTest {

    @Test
    public void testRequiredValidation() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().required();

        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("nonEmpty"));
    }

    @Test
    public void testMinLengthValidation() {
        final int minLength = 5;
        Validator validator = new Validator();
        StringSchema schema = validator.string().minLength(minLength);

        assertFalse(schema.isValid("abcd"));
        assertTrue(schema.isValid("abcdef"));
    }

    @Test
    public void testContainsValidation() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().contains("abc");

        assertFalse(schema.isValid("xyz"));
        assertTrue(schema.isValid("abccba"));
    }

    @Test
    public void testCombinedValidations() {
        final int minLength = 6;
        Validator validator = new Validator();
        StringSchema schema = validator.string().required().minLength(minLength).contains("hex");

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
        Validator validator = new Validator();
        StringSchema schema = validator.string().minLength(minLength1).minLength(minLength2);

        assertFalse(schema.isValid("shor"));
        assertTrue(schema.isValid("thisIsLongEnough"));
    }

    @Test
    public void testNoValidation() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("anything"));
    }

    @Test
    public void testNullInputForValidators() {
        final int minLength = 3;
        Validator validator = new Validator();
        StringSchema schema = validator.string().required().minLength(minLength);

        assertFalse(schema.isValid(null));
    }

    @Test
    public void testEmptyStringForValidators() {
        final int minLength = 3;
        Validator validator = new Validator();
        StringSchema schema = validator.string().required().minLength(minLength);

        assertFalse(schema.isValid(""));
    }

    @Test
    public void testAllValidatorsPass() {
        final int minLength = 7;
        Validator validator = new Validator();
        StringSchema schema = validator.string().required().minLength(minLength).contains("world");

        assertTrue(schema.isValid("Hello world!"));
    }

    @Test
    public void testInvalidStringForContains() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().contains("fox");

        assertFalse(schema.isValid("dog"));
    }

    @Test
    public void testNullForContains() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().contains("fox");

        assertFalse(schema.isValid(null));
    }

    @Test
    public void testSingleCharacterStrings() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().minLength(1);

        assertTrue(schema.isValid("a"));
        assertFalse(schema.isValid(""));
    }

    @Test
    public void testMultipleConstraintsOrder() {
        final int minLength = 5;
        Validator validator = new Validator();
        StringSchema schema = validator.string().required().minLength(minLength).contains("test");

        assertFalse(schema.isValid("tst"));
        assertFalse(schema.isValid("tes"));
        assertTrue(schema.isValid("testtest"));
    }
}
