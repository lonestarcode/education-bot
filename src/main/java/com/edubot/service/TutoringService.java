package com.edubot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages one-on-one tutoring sessions with personalized learning paths,
 * study materials generation, and progress tracking.
 */
@Service
public class TutoringService {
    private static final Logger logger = LoggerFactory.getLogger(TutoringService.class);

    private final LearningAnalyticsService learningAnalyticsService;
    private final StudyMaterialService studyMaterialService;
    private final BotActivationService botActivationService;

    @Autowired
    public TutoringService(
            LearningAnalyticsService learningAnalyticsService,
            StudyMaterialService studyMaterialService,
            BotActivationService botActivationService) {
        this.learningAnalyticsService = learningAnalyticsService;
        this.studyMaterialService = studyMaterialService;
        this.botActivationService = botActivationService;
    }

    /**
     * Initializes a personalized tutoring session
     */
    public TutoringSession startSession(Long studentId) {
        // Get current learning context and materials
        WeeklySummary summary = generateWeeklyOverview(studentId);
        LearningStyle style = learningAnalyticsService.getStudentLearningStyle(studentId);
        SemesterRubric rubric = studyMaterialService.getCurrentRubric();
        
        // Create personalized study path
        StudyPath studyPath = createStudyPath(studentId, style, rubric);
        
        // Generate initial study materials
        StudyMaterials materials = generateStudyMaterials(studentId);
        
        return TutoringSession.builder()
            .studentId(studentId)
            .weeklySummary(summary)
            .studyPath(studyPath)
            .studyMaterials(materials)
            .startTime(LocalDateTime.now())
            .build();
    }

    /**
     * Generates comprehensive study materials
     */
    public StudyMaterials generateStudyMaterials(Long studentId) {
        LearningStyle style = learningAnalyticsService.getStudentLearningStyle(studentId);
        ProficiencyLevel proficiency = learningAnalyticsService.getProficiencyLevel(studentId);
        List<Material> currentMaterials = studyMaterialService.getCurrentWeekMaterials();
        
        return StudyMaterials.builder()
            .quizzes(generateQuizzes(currentMaterials, proficiency))
            .studyGuides(generateStudyGuides(currentMaterials, style))
            .practiceExercises(generateExercises(currentMaterials, proficiency))
            .flashcards(generateFlashcards(currentMaterials, style))
            .summaries(generateSummaries(currentMaterials))
            .build();
    }

    /**
     * Creates detailed weekly overview and progress summary
     */
    private WeeklySummary generateWeeklyOverview(Long studentId) {
        // Gather all relevant materials and progress data
        List<Material> currentMaterials = studyMaterialService.getCurrentWeekMaterials();
        StudentProgress progress = learningAnalyticsService.getProgress(studentId);
        SemesterRubric rubric = studyMaterialService.getCurrentRubric();
        
        return WeeklySummary.builder()
            .weeklyMaterials(currentMaterials)
            .progressMetrics(progress)
            .upcomingAssignments(getUpcomingAssignments(rubric))
            .keyTopics(extractKeyTopics(currentMaterials))
            .recommendedFocus(generateFocusAreas(progress))
            .build();
    }

    /**
     * Creates personalized study path based on multiple factors
     */
    private StudyPath createStudyPath(Long studentId, LearningStyle style, SemesterRubric rubric) {
        StudentProgress progress = learningAnalyticsService.getProgress(studentId);
        List<Material> materials = studyMaterialService.getCurrentWeekMaterials();
        
        return StudyPath.builder()
            .weeklyGoals(generateWeeklyGoals(rubric, progress))
            .learningActivities(generateActivities(style, materials))
            .assessments(generateAssessments(progress.getProficiencyLevel()))
            .milestones(generateMilestones(rubric))
            .build();
    }

    /**
     * Provides intelligent homework assistance
     */
    public HomeworkAssistance getHomeworkHelp(Long studentId, String question) {
        ProficiencyLevel proficiency = learningAnalyticsService.getProficiencyLevel(studentId);
        List<Material> relevantMaterials = studyMaterialService.findRelevantMaterials(question);
        
        return HomeworkAssistance.builder()
            .conceptualHint(generateHint(question, proficiency))
            .relatedTopics(findRelatedTopics(question, relevantMaterials))
            .suggestedResources(recommendResources(question, proficiency))
            .practiceProblems(generateSimilarProblems(question, proficiency))
            .explanations(generateExplanations(question, relevantMaterials))
            .build();
    }

    /**
     * Generates comprehensive progress report
     */
    public ProgressReport generateProgressReport(Long studentId) {
        StudentProgress progress = learningAnalyticsService.getProgress(studentId);
        List<Assessment> assessments = studyMaterialService.getStudentAssessments(studentId);
        
        return ProgressReport.builder()
            .strengths(progress.getStrengths())
            .weaknesses(progress.getWeaknesses())
            .improvements(analyzeImprovements(assessments))
            .recommendations(generateRecommendations(progress))
            .nextSteps(planNextSteps(progress, assessments))
            .build();
    }

    /**
     * Updates student's learning path based on new progress
     */
    public void updateLearningPath(Long studentId, ProgressReport report) {
        LearningStyle style = learningAnalyticsService.getStudentLearningStyle(studentId);
        SemesterRubric rubric = studyMaterialService.getCurrentRubric();
        
        StudyPath updatedPath = createStudyPath(studentId, style, rubric);
        learningAnalyticsService.updateStudentPath(studentId, updatedPath);
    }
} 