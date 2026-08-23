package com.passwordutil;

import io.javalin.Javalin;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Initialize Javalin on Port 7070 and run the web files from /public
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        }).start(7070);

        // API Endpoint: /api/generate
        app.post("/api/generate", ctx -> {
            try {
                // Parse incoming JSON request body into PasswordOptions
                PasswordOptions options = ctx.bodyAsClass(PasswordOptions.class);
                options.validateOptions(); // Trigger validation rules (length & active sets)

                // Core Logic Execution
                PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
                String password = engine.generatePassword(options);

                int poolSize = options.calculatePoolSize();
                double entropy = PasswordStrengthAnalyzer.calculateEntropy(password.length(), poolSize);
                PasswordStrengthAnalyzer.StrengthRating rating = PasswordStrengthAnalyzer.evaluateStrength(entropy);

                // JSON Responses
                ctx.json(Map.of(
                        "password", password,
                        "entropy", entropy,
                        "rating", rating.name()
                ));
            } catch (IllegalArgumentException e) {
                // Return HTTP 400 with the exact validation error message
                ctx.status(400).json(Map.of("message", e.getMessage()));
            } catch (Exception e) {
                // Handle parsing or malformed JSON payload errors
                ctx.status(400).json(Map.of("message", "Invalid request body format."));
            }
        });
    }
}