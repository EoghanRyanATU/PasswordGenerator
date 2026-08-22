package com.passwordutil;

public class Main {
    public static void main(String[] args) {
        PasswordGeneratorEngine engine = new PasswordGeneratorEngine();

        // Test 1: Standard 12-character default password
        PasswordOptions defaultOptions = new PasswordOptions();
        String defaultPass = engine.generatePassword(defaultOptions);
        System.out.println("Default Password (12 chars): " + defaultPass);

        // Test 2: Custom 20-character password with letters & numbers only (no symbols)
        PasswordOptions customOptions = new PasswordOptions(20, true, true, true, false);
        String customPass = engine.generatePassword(customOptions);
        System.out.println("Custom Password (20 chars, no symbols): " + customPass);

        // Test 3: Short 8-character PIN style password (numbers only)
        PasswordOptions numberOptions = new PasswordOptions(8, false, false, true, false);
        String numberPass = engine.generatePassword(numberOptions);
        System.out.println("Numbers-Only Passcode (8 digits): " + numberPass);
    }
}