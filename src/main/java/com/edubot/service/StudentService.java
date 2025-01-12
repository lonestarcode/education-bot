package com.edubot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edubot.model.student.StudentProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Manages student data and facilitates communication with AI/ML microservices.
 */
@Service
@Validated
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final Map<Long, StudentProfile> studentProfiles = new HashMap<>();
    private final Map<Long, List<String>> activityLogs = new HashMap<>();
    private final BotActivationService botActivationService;

    @Autowired
    public StudentService(BotActivationService botActivationService) {
        this.botActivationService = botActivationService;
    }

    // ========================= STUDENT MANAGEMENT =========================

    public String addStudentProfile(@NotNull Long studentId, @NotNull String name) {
        if (studentProfiles.containsKey(studentId)) {
            logger.warn("Student profile already exists: {}", studentId);
            return "Error: Student profile already exists.";
        }
        studentProfiles.put(studentId, new StudentProfile(studentId, name));
        activityLogs.put(studentId, new ArrayList<>());
        logger.info("Student profile added successfully for ID: {}", studentId);
        return "Student profile added successfully.";
    }

    public String removeStudentProfile(@NotNull Long studentId) {
        if (studentProfiles.remove(studentId) != null) {
            activityLogs.remove(studentId);
            logger.info("Student profile removed for ID: {}", studentId);
            return "Student profile removed successfully.";
        }
        logger.warn("Attempt to remove non-existing Student ID: {}", studentId);
        return "Error: Student ID not found.";
    }

    private boolean validateStudent(Long studentId) {
        return studentProfiles.containsKey(studentId);
    }

    // ========================= INTERACTION WITH PYTHON SERVICES =========================

    public String askQuestion(@NotNull Long studentId, @NotNull String question) {
        validateStudentExistence(studentId);
        logger.info("Student {} asked a question: {}", studentId, question);
        String response = botActivationService.generateEducationalContent(question);
        logActivity(studentId, "Asked Question: " + question);
        return response;
    }

    public String getHomeworkHint(@NotNull Long studentId, @NotNull String homeworkQuestion) {
        validateStudentExistence(studentId);
        logger.info("Homework hint requested by Student ID {}: {}", studentId, homeworkQuestion);
        String hint = botActivationService.generateEducationalContent("Hint: " + homeworkQuestion);
        logActivity(studentId, "Requested Homework Hint: " + homeworkQuestion);
        return hint;
    }

    private void validateStudentExistence(Long studentId) {
        if (!validateStudent(studentId)) {
            logger.warn("Invalid student ID: {}", studentId);
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }

    private void logActivity(Long studentId, String activity) {
        activityLogs.computeIfAbsent(studentId, k -> new ArrayList<>()).add(activity);
        logger.info("Activity logged for Student ID {}: {}", studentId, activity);
    }

    public List<String> getActivityLog(@NotNull Long studentId) {
        return activityLogs.getOrDefault(studentId, List.of("No activities recorded."));
    }
}