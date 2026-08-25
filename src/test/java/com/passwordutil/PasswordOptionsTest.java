package com.passwordutil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PasswordOptionsTest {

    @Test
    void testDefaultValues() {
        PasswordOptions options = new PasswordOptions();
        assertEquals(12, options.getLength());
        assertTrue(options.isUseUppercase());
        assertTrue(options.isUseLowercase());
        assertTrue(options.isUseNumbers());
        assertTrue(options.isUseSymbols());
    }

    @Test
    void testInvalidLengthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new PasswordOptions(4, true, true, true, true));
        assertThrows(IllegalArgumentException.class, () -> new PasswordOptions(200, true, true, true, true));
    }

    @Test
    void testNoSelectionThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new PasswordOptions(10, false, false, false, false)
        );
    }
    @Test
    void testPasswordGenerationLength() {
        PasswordOptions options = new PasswordOptions(16, true, true, true, true);
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
        String password = engine.generatePassword(options);

        assertNotNull(password);
        assertEquals(16, password.length());
    }

    @Test
    @DisplayName("Verify individual setters update state correctly")
    void testSettersAndGetters() {
        PasswordOptions options = new PasswordOptions();

        options.setUseUppercase(false);
        options.setUseLowercase(false);
        options.setUseNumbers(true);
        options.setUseSymbols(true);

        assertFalse(options.isUseUppercase());
        assertFalse(options.isUseLowercase());
        assertTrue(options.isUseNumbers());
        assertTrue(options.isUseSymbols());
    }

    @Test
    @DisplayName("Verify pool size calculations across character combinations")
    void testCalculatePoolSizeBranches() {
        // Uppercase only (26)
        PasswordOptions upperOnly = new PasswordOptions(12, true, false, false, false);
        assertEquals(26, upperOnly.calculatePoolSize());

        // Lowercase only (26)
        PasswordOptions lowerOnly = new PasswordOptions(12, false, true, false, false);
        assertEquals(26, lowerOnly.calculatePoolSize());

        // Numbers only (10)
        PasswordOptions numbersOnly = new PasswordOptions(12, false, false, true, false);
        assertEquals(10, numbersOnly.calculatePoolSize());

        // Symbols only (32)
        PasswordOptions symbolsOnly = new PasswordOptions(12, false, false, false, true);
        assertEquals(32, symbolsOnly.calculatePoolSize());

        // All combined (26 + 26 + 10 + 32 = 94)
        PasswordOptions allSets = new PasswordOptions(12, true, true, true, true);
        assertEquals(94, allSets.calculatePoolSize());
    }
}