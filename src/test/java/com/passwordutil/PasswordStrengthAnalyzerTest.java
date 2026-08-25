package com.passwordutil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordStrengthAnalyzerTest {

    @Test
    void testCalculateEntropyValid() {
        // Full pool (94 chars) at length 12
        double entropy = PasswordStrengthAnalyzer.calculateEntropy(12, 94);
        assertTrue(entropy > 78.0 && entropy < 79.0, "Entropy should be around 78.65 bits");
    }

    @Test
    void testCalculateEntropyZeroOrNegative() {
        // Verify edge case handling for invalid bounds
        assertEquals(0.0, PasswordStrengthAnalyzer.calculateEntropy(0, 94));
        assertEquals(0.0, PasswordStrengthAnalyzer.calculateEntropy(12, 0));
    }

    @Test
    void testEvaluateStrengthRatings() {
        // Enforcing conditions for Weak, Medium, and Strong thresholds
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.WEAK,
                PasswordStrengthAnalyzer.evaluateStrength(35.0));
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.MEDIUM,
                PasswordStrengthAnalyzer.evaluateStrength(45.0));
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.STRONG,
                PasswordStrengthAnalyzer.evaluateStrength(78.5));
    }

    // Tests and verifies the thresholds for WEAK, MEDIUM, and STRONG password ratings
    @Test
    @DisplayName("Verify strength rating boundary thresholds")
    void testEvaluateStrengthBoundaries() {
        // WEAK tier
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.WEAK,
                PasswordStrengthAnalyzer.evaluateStrength(20.0));

        // MEDIUM tier
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.MEDIUM,
                PasswordStrengthAnalyzer.evaluateStrength(45.0));

        // STRONG tier
        assertEquals(PasswordStrengthAnalyzer.StrengthRating.STRONG,
                PasswordStrengthAnalyzer.evaluateStrength(80.0));
    }
}