package com.passwordutil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorEngineTest {

    @Test
    @DisplayName("Verify generated password matches requested length")
    void testGeneratedPasswordLength() {
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
        PasswordOptions options = new PasswordOptions(16, true, true, true, true);

        String password = engine.generatePassword(options);

        assertNotNull(password);
        assertEquals(16, password.length());
    }

    @Test
    @DisplayName("Verify boundary length generation for 8 and 128 characters")
    void testBoundaryValueLengths() {
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();

        PasswordOptions minOptions = new PasswordOptions(8, true, true, true, true);
        assertEquals(8, engine.generatePassword(minOptions).length());

        PasswordOptions maxOptions = new PasswordOptions(128, true, true, true, true);
        assertEquals(128, engine.generatePassword(maxOptions).length());
    }

    @Test
    @DisplayName("Verify generated password contains only numbers when digits-only option is set")
    void testNumbersOnlyGeneration() {
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
        PasswordOptions options = new PasswordOptions(12, false, false, true, false);

        String password = engine.generatePassword(options);

        assertTrue(password.matches("^[0-9]+$"));
    }

    @Test
    @DisplayName("Verify null options parameter throws IllegalArgumentException")
    void testNullOptionsThrowsException() {
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.generatePassword(null));
    }
}