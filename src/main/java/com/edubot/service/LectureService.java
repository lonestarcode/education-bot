package com.edubot.service;

import com.edubot.model.LectureContent;
import com.edubot.repository.LectureContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing classroom-style lectures with Q&A functionality.
 * Supports monologue delivery and replay features.
 */
@Service
public class LectureService {
    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    private LectureContentRepository lectureContentRepository;

    @Autowired
    private StudyMaterialService studyMaterialService;

    @Autowired
    private BotActivationService botActivationService;

    /**
     * Creates a new classroom-style lecture
     */
    public LectureContent createLecture(LectureContent lectureContent) {
        // Set lecture delivery parameters
        lectureContent.setDeliveryStyle("CLASSROOM_MONOLOGUE");
        lectureContent.setInteractionType("CLASS_QA");
        lectureContent.setReplayEnabled(true);
        lectureContent.setCreatedAt(LocalDateTime.now());
        
        return lectureContentRepository.save(lectureContent);
    }

    /**
     * Retrieves a lecture by ID with validation
     */
    public LectureContent getLectureById(Long lectureId) {
        return lectureContentRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with ID: " + lectureId));
    }

    /**
     * Retrieves all available lectures
     */
    public List<LectureContent> getAllLectures() {
        return lectureContentRepository.findAll();
    }

    /**
     * Updates an existing lecture while preserving delivery style
     */
    public LectureContent updateLecture(Long lectureId, LectureContent updatedContent) {
        LectureContent existingLecture = getLectureById(lectureId);
        
        // Update content while preserving delivery settings
        existingLecture.setTitle(updatedContent.getTitle());
        existingLecture.setContentBody(updatedContent.getContentBody());
        existingLecture.setUpdatedAt(LocalDateTime.now());
        
        // Preserve delivery settings
        existingLecture.setDeliveryStyle("CLASSROOM_MONOLOGUE");
        existingLecture.setInteractionType("CLASS_QA");
        existingLecture.setReplayEnabled(true);
        
        return lectureContentRepository.save(existingLecture);
    }

    /**
     * Processes lecture content for delivery
     */
    public String processLectureContent(Long lectureId) {
        LectureContent lecture = getLectureById(lectureId);
        
        // Process attached study materials
        String processedContent = studyMaterialService.processMaterialPDF(
            lecture.getStudyMaterial().getFilePath()
        );
        
        // Format for classroom delivery
        return botActivationService.formatForClassroomDelivery(processedContent);
    }

    /**
     * Handles classroom Q&A during lecture
     */
    public String handleClassroomQuestion(Long lectureId, String question) {
        LectureContent lecture = getLectureById(lectureId);
        
        // Generate response in teaching voice
        return botActivationService.generateClassroomResponse(
            question,
            lecture.getContentBody(),
            "CLASSROOM_VOICE"
        );
    }

    /**
     * Retrieves lecture recording for replay
     */
    public LectureContent getReplayContent(Long lectureId) {
        LectureContent lecture = getLectureById(lectureId);
        if (!lecture.isReplayEnabled()) {
            throw new IllegalStateException("Replay is not enabled for this lecture");
        }
        return lecture;
    }

    /**
     * Deletes a lecture and associated content
     */
    public void deleteLecture(Long lectureId) {
        lectureContentRepository.deleteById(lectureId);
    }
}