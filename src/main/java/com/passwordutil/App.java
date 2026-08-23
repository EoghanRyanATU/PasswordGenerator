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
            // Parse incoming JSON request
            PasswordOptions options;
            try {
                options = ctx.bodyAsClass(PasswordOptions.class);
            } catch (Exception e) {
                options = new PasswordOptions();
            }

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
        });
    }
}