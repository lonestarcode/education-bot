package com.edubot.api.v1.Subject;

import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.SUBJECTS.EnglishRequestDTO;
import com.edubot.dto.SUBJECTS.EnglishResponseDTO;
import com.edubot.service.subject.EnglishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to English lectures, exercises, and content management.
 */
@RestController
@RequestMapping("/api/english")
public class EnglishController {

    private static final Logger logger = LoggerFactory.getLogger(EnglishController.class);

    private final EnglishService englishService;

    @Autowired
    public EnglishController(EnglishService englishService) {
        this.englishService = englishService;
    }

    // ========================= English Content Management =========================

    /**
     * Create new English content.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createEnglishContent(@Valid @RequestBody EnglishRequestDTO englishDTO) {
        logger.info("Creating English content: {}", englishDTO.getTitle());
        englishService.createEnglishContent(englishDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("English content created successfully."));
    }

    /**
     * Get all English content.
     */
    @GetMapping
    public ResponseEntity<List<EnglishResponseDTO>> getAllEnglishContent() {
        logger.info("Fetching all English content.");
        return ResponseEntity.ok(englishService.getAllEnglishContent());
    }

    /**
     * Get specific English content by ID.
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<EnglishResponseDTO> getEnglishContentById(@PathVariable Long contentId) {
        logger.info("Fetching English content with ID: {}", contentId);
        return englishService.getEnglishContentById(contentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update existing English content.
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateEnglishContent(
            @PathVariable Long contentId,
            @Valid @RequestBody EnglishRequestDTO englishDTO) {
        logger.info("Updating English content with ID: {}", contentId);
        englishService.updateEnglishContent(contentId, englishDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("English content updated successfully."));
    }

    /**
     * Delete English content by ID.
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteEnglishContent(@PathVariable Long contentId) {
        logger.info("Deleting English content with ID: {}", contentId);
        englishService.deleteEnglishContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("English content deleted successfully."));
    }

    // ========================= English Exercises =========================

    /**
     * Start an English exercise.
     */
    @PostMapping("/{contentId}/start-exercise")
    public ResponseEntity<ResponseMessageDTO> startExercise(@PathVariable Long contentId) {
        logger.info("Starting exercise for English content ID: {}", contentId);
        englishService.startExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("English exercise started successfully."));
    }

    /**
     * End an English exercise.
     */
    @PostMapping("/{contentId}/end-exercise")
    public ResponseEntity<ResponseMessageDTO> endExercise(@PathVariable Long contentId) {
        logger.info("Ending exercise for English content ID: {}", contentId);
        englishService.endExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("English exercise ended successfully."));
    }

    /**
     * Add a tag to English content.
     */
    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<ResponseMessageDTO> addTag(
            @PathVariable Long contentId,
            @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to English content with ID: {}", tag, contentId);
        englishService.addTag(contentId, tag);
        return ResponseEntity.ok(new ResponseMessageDTO("Tag added successfully."));
    }
}