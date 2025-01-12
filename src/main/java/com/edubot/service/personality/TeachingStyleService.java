package com.edubot.service.personality;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Manages how the bot communicates and teaches, evolving its teaching style
 * through AI-driven personality adaptation.
 */
@Service
public class TeachingStyleService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalityEvolutionService.class);

    @Value("${ai.personalityEvolution.endpoint:http://localhost:5000/personality-evolution}")
    private String personalityEvolutionEndpoint;

    private final HttpClient httpClient;

    public PersonalityEvolutionService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
    }

    /**
     * Analyze and evolve personality based on recent interactions.
     *
     * @param userId Unique identifier for the user.
     * @param interactionData Interaction data from user sessions.
     * @return Summary of personality evolution.
     */
    public String evolvePersonality(Long userId, String interactionData) {
        logger.info("Evolving personality for User ID: {}", userId);
        try {
            String payload = String.format("{\"userId\": %d, \"interactionData\": \"%s\"}", userId, interactionData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(personalityEvolutionEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Personality evolution completed with status: {}", response.statusCode());

            return response.body();
        } catch (Exception e) {
            logger.error("Error evolving personality: {}", e.getMessage());
            return "Error evolving personality: " + e.getMessage();
        }
    }
}