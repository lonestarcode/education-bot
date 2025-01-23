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
    private final PythonScriptExecutor pythonExecutor;

    public LearningAnalyticsService(StudentRepository studentRepository, PythonScriptExecutor pythonExecutor) {
        this.studentRepository = studentRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
        this.pythonExecutor = pythonExecutor;
    }

    /**
     * Comprehensive student analysis combining behavior and learning patterns
     */
    public StudentProfile analyzeStudent(Long studentId) {
        List<Interaction> interactions = getStudentInteractions(studentId);
        PerformanceMetrics metrics = getPerformanceMetrics(studentId);
        Map<String, String> behaviorTrends = analyzeBehaviorTrends(studentId);
        
        return new StudentProfile.Builder()
            .withLearningStyle(analyzeLearningPattern(interactions))
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

    public LearningProfile analyzeLearningPattern(List<Interaction> interactions) {
        // Convert interactions to feature matrix
        double[][] featureMatrix = convertInteractionsToFeatures(interactions);
        
        // Call Python clustering service
        Map<String, Object> results = pythonExecutor.executeScript(
            "sklearn_clustering_service.py",
            "analyze_learning_patterns",
            featureMatrix
        );
        
        return new LearningProfile(
            (Integer) results.get("learning_style_cluster"),
            (Map<String, Object>) results.get("cluster_profile"),
            (List<Integer>) results.get("peer_group"),
            (List<String>) results.get("recommended_materials")
        );
    }
    
    private double[][] convertInteractionsToFeatures(List<Interaction> interactions) {
        // Convert student interactions into a feature matrix for clustering
        // Features might include:
        // - Time spent on different types of content
        // - Performance on assessments
        // - Interaction patterns with different learning materials
        // ... implementation details ...
        return new double[0][0]; // Placeholder
    }
} 