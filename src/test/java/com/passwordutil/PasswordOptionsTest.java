package com.passwordutil;

import org.junit.jupiter.api.Test;
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
}