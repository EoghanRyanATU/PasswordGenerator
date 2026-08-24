package com.passwordutil;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorEngine {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    private final SecureRandom random = new SecureRandom();

    public String generatePassword(PasswordOptions options) {
        if (options == null){
            throw new IllegalArgumentException("Password options cannot be empty!");
        }
        options.validateOptions();

        StringBuilder pool = new StringBuilder();
        List<Character> passwordChars = new ArrayList<>();

        // Add character pools based on user options and guarantee at least 1 character from each chosen set
        if (options.isUseUppercase()) {
            pool.append(UPPER);
            passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        }
        if (options.isUseLowercase()) {
            pool.append(LOWER);
            passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        }
        if (options.isUseNumbers()) {
            pool.append(DIGITS);
            passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        if (options.isUseSymbols()) {
            pool.append(SYMBOLS);
            passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }

        // Fill remaining length using the full allowed character pool
        String fullPool = pool.toString();
        while (passwordChars.size() < options.getLength()) {
            passwordChars.add(fullPool.charAt(random.nextInt(fullPool.length())));
        }

        // Shuffle so guaranteed chars aren't always at the start
        Collections.shuffle(passwordChars, random);

        // Build string from list
        StringBuilder result = new StringBuilder();
        for (char c : passwordChars) {
            result.append(c);
        }

        return result.toString();
    }
}