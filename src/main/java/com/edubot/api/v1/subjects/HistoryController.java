package com.edubot.api.v1.Subject;

import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.SUBJECTS.HistoryRequestDTO;
import com.edubot.dto.SUBJECTS.HistoryResponseDTO;
import com.edubot.service.subject.HistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to history lectures, exercises, and content management.
 */
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    // ========================= History Content Management =========================

    /**
     * Create new history content.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createHistoryContent(@Valid @RequestBody HistoryRequestDTO historyDTO) {
        logger.info("Creating history content: {}", historyDTO.getTitle());
        historyService.createHistoryContent(historyDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("History content created successfully."));
    }

    /**
     * Get all history content.
     */
    @GetMapping
    public ResponseEntity<List<HistoryResponseDTO>> getAllHistoryContent() {
        logger.info("Fetching all history content.");
        return ResponseEntity.ok(historyService.getAllHistoryContent());
    }

    /**
     * Get specific history content by ID.
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<HistoryResponseDTO> getHistoryContentById(@PathVariable Long contentId) {
        logger.info("Fetching history content with ID: {}", contentId);
        return historyService.getHistoryContentById(contentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update existing history content.
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateHistoryContent(
            @PathVariable Long contentId,
            @Valid @RequestBody HistoryRequestDTO historyDTO) {
        logger.info("Updating history content with ID: {}", contentId);
        historyService.updateHistoryContent(contentId, historyDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("History content updated successfully."));
    }

    /**
     * Delete history content by ID.
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteHistoryContent(@PathVariable Long contentId) {
        logger.info("Deleting history content with ID: {}", contentId);
        historyService.deleteHistoryContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("History content deleted successfully."));
    }

    // ========================= History Exercises =========================

    /**
     * Start a history exercise.
     */
    @PostMapping("/{contentId}/start-exercise")
    public ResponseEntity<ResponseMessageDTO> startExercise(@PathVariable Long contentId) {
        logger.info("Starting exercise for history content ID: {}", contentId);
        historyService.startExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("History exercise started successfully."));
    }

    /**
     * End a history exercise.
     */
    @PostMapping("/{contentId}/end-exercise")
    public ResponseEntity<ResponseMessageDTO> endExercise(@PathVariable Long contentId) {
        logger.info("Ending exercise for history content ID: {}", contentId);
        historyService.endExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("History exercise ended successfully."));
    }

    /**
     * Add a tag to history content.
     */
    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<ResponseMessageDTO> addTag(
            @PathVariable Long contentId,
            @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to history content with ID: {}", tag, contentId);
        historyService.addTag(contentId, tag);
        return ResponseEntity.ok(new ResponseMessageDTO("Tag added successfully."));
    }
}