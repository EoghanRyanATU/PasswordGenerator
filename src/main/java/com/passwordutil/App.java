package com.passwordutil;

import io.javalin.Javalin;
import java.util.Map;

public class App {

    // ADDED: method that returns the configured Javalin instance
    public static Javalin createApp() {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });

        // API Endpoint: /api/generate
        app.post("/api/generate", ctx -> {
            try {
                PasswordOptions options = ctx.bodyAsClass(PasswordOptions.class);
                options.validateOptions();

                PasswordGeneratorEngine engine = new PasswordGeneratorEngine();
                String password = engine.generatePassword(options);

                int poolSize = options.calculatePoolSize();
                double entropy = PasswordStrengthAnalyzer.calculateEntropy(password.length(), poolSize);
                PasswordStrengthAnalyzer.StrengthRating rating = PasswordStrengthAnalyzer.evaluateStrength(entropy);

                ctx.json(Map.of(
                        "password", password,
                        "entropy", entropy,
                        "rating", rating.name()
                ));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("message", e.getMessage()));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("message", "Invalid request body format."));
            }
        });

        return app;
    }

    // CHANGED: Main method now just delegates to createApp() and starts the server
    public static void main(String[] args) {
        createApp().start(7070);
    }
}