package com.passwordutil;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    @DisplayName("Verify App class default constructor execution")
    void testAppConstructor() {
        App appInstance = new App();
        assertNotNull(appInstance);
    }

    @Test
    @DisplayName("POST /api/generate - Success returns HTTP 200 with generated payload")
    void testGenerateEndpointSuccess() {
        Javalin app = App.createApp();

        JavalinTest.test(app, (server, client) -> {
            // Uses exact JSON keys mapped by @JsonProperty annotations in PasswordOptions
            String jsonPayload = "{"
                    + "\"length\":16,"
                    + "\"useUpper\":true,"
                    + "\"useLower\":true,"
                    + "\"useNumbers\":true,"
                    + "\"useSymbols\":true"
                    + "}";

            try (var response = client.post("/api/generate", jsonPayload)) {
                assertEquals(200, response.code());
                assertNotNull(response.body());

                String responseBody = response.body().string();
                assertTrue(responseBody.contains("password"));
                assertTrue(responseBody.contains("entropy"));
                assertTrue(responseBody.contains("rating"));
            }
        });
    }

    @Test
    @DisplayName("POST /api/generate - Validation failure triggers IllegalArgumentException (HTTP 400)")
    void testGenerateEndpointIllegalArgument() {
        Javalin app = App.createApp();

        JavalinTest.test(app, (server, client) -> {
            // Invalid length (< 8) triggers IllegalArgumentException inside setLength/validateOptions
            String invalidPayload = "{"
                    + "\"length\":4,"
                    + "\"useUpper\":true,"
                    + "\"useLower\":true,"
                    + "\"useNumbers\":true,"
                    + "\"useSymbols\":true"
                    + "}";

            try (var response = client.post("/api/generate", invalidPayload)) {
                assertEquals(400, response.code());
                assertNotNull(response.body());
                assertTrue(response.body().string().contains("message"));
            }
        });
    }

    @Test
    @DisplayName("POST /api/generate - Malformed JSON triggers generic Exception catch (HTTP 400)")
    void testGenerateEndpointMalformedJson() {
        Javalin app = App.createApp();

        JavalinTest.test(app, (server, client) -> {
            String malformedJson = "{\"length\": invalid_json_syntax}";

            try (var response = client.post("/api/generate", malformedJson)) {
                assertEquals(400, response.code());
                assertNotNull(response.body());
                assertTrue(response.body().string().contains("Invalid request body format."));
            }
        });
    }
}