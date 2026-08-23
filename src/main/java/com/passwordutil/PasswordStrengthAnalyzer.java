package com.passwordutil;

// Utility class to evaluate password strength using mathematical entropy bits
// and map the result to strength ratings (Weak, Medium, Strong).

public class PasswordStrengthAnalyzer {

    // Enum to represent discrete strength levels for API standardisation
    public enum StrengthRating {
        WEAK,
        MEDIUM,
        STRONG
    }

    // Calculates bit entropy using length L and character pool size

    public static double calculateEntropy(int length, int poolSize) {
        // Guard against invalid bounds or empty character pools
        if (length <= 0 || poolSize <= 0) {
            return 0.0;
        }

        // Log base change rule: log2(R) = ln(R) / ln(2)
        return length * (Math.log(poolSize) / Math.log(2));
    }

    // Maps entropy bits to strength thresholds

    public static StrengthRating evaluateStrength(double entropyBits) {
        if (entropyBits < 40.0) {
            return StrengthRating.WEAK;     // Vulnerable to rapid brute-force attacks
        } else if (entropyBits < 60.0) {
            return StrengthRating.MEDIUM;   // Reasonable protection for basic use
        } else {
            return StrengthRating.STRONG;   // High security against dictionary/online attacks
        }
    }
}