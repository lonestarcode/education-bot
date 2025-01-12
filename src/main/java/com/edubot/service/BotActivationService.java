package com.edubot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * BotActivationService acts as a gateway for Python AI/ML microservices.
 * Handles content generation, quiz generation, summaries, and PDF operations.
 */
@Service
public class BotActivationService {

    private static final Logger logger = LoggerFactory.getLogger(BotActivationService.class);

    @Value("${ai.content.endpoint:http://localhost:5000/content}")
    private String contentEndpoint;

    @Value("${ai.quiz.endpoint:http://localhost:5000/quiz}")
    private String quizEndpoint;

    @Value("${ai.summary.endpoint:http://localhost:5000/summarize}")
    private String summaryEndpoint;

    @Value("${ai.pdf.endpoint:http://localhost:5000/pdf}")
    private String pdfEndpoint;

    @Value("${ai.http.timeout:10}")
    private int httpTimeout;

    private final HttpClient httpClient;

    public BotActivationService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(httpTimeout))
                .build();
    }

    // ========================= AI OPERATIONS =========================

    public String generateContent(String inputText) {
        return sendRequest(contentEndpoint, inputText, "content");
    }

    public String generateQuiz(String inputText) {
        return sendRequest(quizEndpoint, inputText, "quiz");
    }

    public String summarizeText(String inputText) {
        return sendRequest(summaryEndpoint, inputText, "summary");
    }

    // ========================= PDF OPERATIONS =========================

    public String extractText(String filePath) {
        return sendRequest(pdfEndpoint + "/extract", filePath, "extractText");
    }

    public String getMetadata(String filePath) {
        return sendRequest(pdfEndpoint + "/metadata", filePath, "getMetadata");
    }

    public String searchKeywords(String filePath, List<String> keywords) {
        JSONObject payload = new JSONObject();
        payload.put("filePath", filePath);
        payload.put("keywords", keywords);

        return sendRequest(pdfEndpoint + "/search", payload.toString(), "searchKeywords");
    }

    public String processPDF(String filePath) {
        String extractedText = extractText(filePath);
        return summarizeText(extractedText);
    }

    // ========================= UTILITY METHOD =========================

    private String sendRequest(String endpoint, String payload, String task) {
        try {
            JSONObject json = new JSONObject();
            json.put("input", payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.error("Failed to perform {}. HTTP Status: {}", task, response.statusCode());
                throw new RuntimeException("Failed to perform " + task + ". HTTP Status: " + response.statusCode());
            }

            return response.body();
        } catch (Exception e) {
            logger.error("Error while communicating with {} API: {}", task, e.getMessage());
            return "Error processing " + task + ": " + e.getMessage();
        }
    }
}