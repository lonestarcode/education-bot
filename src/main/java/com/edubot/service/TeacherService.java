package com.edubot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edubot.model.teacher.Lecture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.*;

/**
 * Manages teacher-level interactions including lecture scheduling, material uploads, and AI/ML integrations.
 */
@Service
@Validated
public class TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private final StudyMaterialService studyMaterialService;
    private final PDForchestration pdfProcessingService;
    private final BotActivationService botActivationService;

    private final Map<String, Map<String, List<Lecture>>> topicLectures = new HashMap<>();
    private final Map<Long, List<Long>> studentParticipation = new HashMap<>();

    @Autowired
    public TeacherService(
            StudyMaterialService studyMaterialService,
            PDForchestration pdfProcessingService,
            BotActivationService botActivationService) {
        this.studyMaterialService = studyMaterialService;
        this.pdfProcessingService = pdfProcessingService;
        this.botActivationService = botActivationService;
    }

    public void scheduleLecture(@NotNull String subject, @NotNull String topic, @NotNull Lecture lecture) {
        topicLectures
                .computeIfAbsent(subject, k -> new HashMap<>())
                .computeIfAbsent(topic, k -> new ArrayList<>())
                .add(lecture);
        logger.info("Scheduled lecture '{}' under subject '{}' and topic '{}'.", lecture.getTitle(), subject, topic);
    }

    public List<Lecture> getLecturesByTopic(@NotNull String subject, @NotNull String topic) {
        return topicLectures.getOrDefault(subject, Collections.emptyMap())
                            .getOrDefault(topic, Collections.emptyList());
    }

    public String uploadAndActivateMaterial(@NotNull String materialTitle, @NotNull String filePath, boolean useSummarization) {
        validateInputs(materialTitle, filePath);
        String rawText = pdfProcessingService.extractText(filePath);
        String content = useSummarization ? botActivationService.summarizeText(rawText) : rawText;
        studyMaterialService.addMaterial(materialTitle, content, "General");
        return "Material uploaded and activated successfully.";
    }

    public void trackParticipation(@NotNull Long studentId, @NotNull Long lectureId) {
        studentParticipation.computeIfAbsent(studentId, k -> new ArrayList<>()).add(lectureId);
        logger.info("Student ID {} participated in lecture ID {}", studentId, lectureId);
    }

    private void validateInputs(String materialTitle, String filePath) {
        if (!new File(filePath).exists()) {
            throw new IllegalArgumentException("The provided file does not exist: " + filePath);
        }
    }
} 