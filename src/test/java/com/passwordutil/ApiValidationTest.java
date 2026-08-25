package com.passwordutil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiValidationTest {

    @Test
    @DisplayName("Verify API payload processes valid options successfully")
    void testValidApiPayloadHandling() {
        PasswordOptions options = new PasswordOptions(16, true, true, true, true);
        assertDoesNotThrow(options::validateOptions);
        assertEquals(16, options.getLength());
        assertEquals(94, options.calculatePoolSize());
    }

    @Test
    @DisplayName("Verify API payload rejects invalid length (< 8)")
    void testInvalidLengthApiPayload() {
        // Exception thrown in constructor by setLength(4)
        assertThrows(
                IllegalArgumentException.class,
                () -> new PasswordOptions(4, true, true, true, true)
        );
    }

    @Test
    @DisplayName("Verify API payload rejects when all character sets are disabled")
    void testNoCharacterSetsSelectedApiPayload() {
        // Exception thrown in constructor by validateOptions()
        assertThrows(
                IllegalArgumentException.class,
                () -> new PasswordOptions(12, false, false, false, false)
        );
    }
}