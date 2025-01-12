package com.edubot.service.personality;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analyzes student behavior, tracks progress, and adapts learning content
 * based on comprehensive analytics.
 */
@Service
public class LearningAnalyticsService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalityAdaptiveService.class);

    @Value("${ai.behaviorAnalysis.endpoint}")
    private String behaviorAnalysisEndpoint;

    @Value("${ai.adaptiveLearning.endpoint}")
    private String adaptiveLearningEndpoint;

    private final HttpClient httpClient;
    private final StudentRepository studentRepository;

    public PersonalityAdaptiveService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
    }

    /**
     * Comprehensive student analysis combining behavior and learning patterns
     */
    public StudentProfile analyzeStudent(Long studentId) {
        List<Interaction> interactions = getStudentInteractions(studentId);
        PerformanceMetrics metrics = getPerformanceMetrics(studentId);
        Map<String, String> behaviorTrends = analyzeBehaviorTrends(studentId);
        
        return new StudentProfile.Builder()
            .withLearningStyle(analyzeLearningPattern(interactions, metrics))
            .withBehaviorProfile(analyzeBehavior(studentId, interactions))
            .withStrengths(analyzeStrengths(metrics))
            .withWeaknesses(analyzeWeaknesses(metrics))
            .withEngagementMetrics(behaviorTrends)
            .build();
    }

    /**
     * Process new interaction data and update profiles
     */
    public void processInteraction(InteractionData interaction) {
        // Update both learning and behavior patterns
        updateLearningPatterns(interaction);
        analyzeBehavior(interaction.getStudentId(), interaction.toString());
        
        if (shouldUpdateProfile(interaction)) {
            recalculateProfile(interaction.getStudentId());
        }
    }

    /**
     * Generate personalized learning strategy based on behavior and performance
     */
    public LearningStrategy generateStrategy(Long studentId) {
        StudentProfile profile = analyzeStudent(studentId);
        ProficiencyLevel proficiency = calculateProficiencyLevel(studentId);
        Map<String, String> behaviorTrends = getBehaviorTrends(studentId);
        
        return new LearningStrategy.Builder()
            .withTeachingStyle(profile.getLearningStyle())
            .withDifficultyLevel(proficiency)
            .withFocusAreas(profile.getWeaknesses())
            .withEngagementApproach(determineEngagementStrategy(behaviorTrends))
            .build();
    }

    private String analyzeBehavior(Long userId, String behaviorData) {
        try {
            String payload = String.format("{\"userId\": %d, \"behaviorData\": \"%s\"}", userId, behaviorData);
            return sendRequest(behaviorAnalysisEndpoint, payload);
        } catch (Exception e) {
            logger.error("Error analyzing behavior: {}", e.getMessage());
            return "Error analyzing behavior: " + e.getMessage();
        }
    }

    private Map<String, String> getBehaviorTrends(Long userId) {
        logger.info("Fetching behavioral trends for User ID: {}", userId);
        return sendRequest(behaviorAnalysisEndpoint + "/trends/" + userId);
    }

    private LearningStyle analyzeLearningPattern(List<Interaction> interactions, PerformanceMetrics metrics) {
        Map<String, Object> analysisData = new HashMap<>();
        analysisData.put("interactions", interactions);
        analysisData.put("metrics", metrics);
        return sendRequest(adaptiveLearningEndpoint, analysisData);
    }
} 