package com.edubot.api.v1.subjects;

import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.SUBJECTS.ArtRequestDTO;
import com.edubot.dto.SUBJECTS.ArtResponseDTO;
import com.edubot.service.subject.ArtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to Art lectures, exercises, and content management.
 */
@RestController
@RequestMapping("/api/art")
public class ArtController {

    private static final Logger logger = LoggerFactory.getLogger(ArtController.class);

    private final ArtService artService;

    @Autowired
    public ArtController(ArtService artService) {
        this.artService = artService;
    }

    // ========================= Art Content Management =========================

    /**
     * Create new Art content.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createArtContent(@Valid @RequestBody ArtRequestDTO artDTO) {
        logger.info("Creating Art content: {}", artDTO.getTitle());
        artService.createArtContent(artDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Art content created successfully."));
    }

    /**
     * Get all Art content.
     */
    @GetMapping
    public ResponseEntity<List<ArtResponseDTO>> getAllArtContent() {
        logger.info("Fetching all Art content.");
        return ResponseEntity.ok(artService.getAllArtContent());
    }

    /**
     * Get specific Art content by ID.
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<ArtResponseDTO> getArtContentById(@PathVariable Long contentId) {
        logger.info("Fetching Art content with ID: {}", contentId);
        ArtResponseDTO response = artService.getArtContentById(contentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Update existing Art content.
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateArtContent(
            @PathVariable Long contentId,
            @Valid @RequestBody ArtRequestDTO artDTO) {
        logger.info("Updating Art content with ID: {}", contentId);
        artService.updateArtContent(contentId, artDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Art content updated successfully."));
    }

    /**
     * Delete Art content by ID.
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteArtContent(@PathVariable Long contentId) {
        logger.info("Deleting Art content with ID: {}", contentId);
        artService.deleteArtContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Art content deleted successfully."));
    }

    // ========================= Art Exercises =========================

    /**
     * Start an Art exercise.
     */
    @PostMapping("/{contentId}/start-exercise")
    public ResponseEntity<ResponseMessageDTO> startExercise(@PathVariable Long contentId) {
        logger.info("Starting exercise for Art content ID: {}", contentId);
        artService.startExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Art exercise started successfully."));
    }

    /**
     * End an Art exercise.
     */
    @PostMapping("/{contentId}/end-exercise")
    public ResponseEntity<ResponseMessageDTO> endExercise(@PathVariable Long contentId) {
        logger.info("Ending exercise for Art content ID: {}", contentId);
        artService.endExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Art exercise ended successfully."));
    }

    /**
     * Add a tag to Art content.
     */
    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<ResponseMessageDTO> addTag(
            @PathVariable Long contentId,
            @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to Art content with ID: {}", tag, contentId);
        artService.addTag(contentId, tag);
        return ResponseEntity.ok(new ResponseMessageDTO("Tag added successfully."));
    }
}