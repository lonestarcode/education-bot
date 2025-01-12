package com.edubot.api.v1.Subject;

import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.SUBJECTS.SpanishRequestDTO;
import com.edubot.dto.SUBJECTS.SpanishResponseDTO;
import com.edubot.service.subject.SpanishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to Spanish lectures, exercises, and content management.
 */
@RestController
@RequestMapping("/api/spanish")
public class SpanishController {

    private static final Logger logger = LoggerFactory.getLogger(SpanishController.class);

    private final SpanishService spanishService;

    @Autowired
    public SpanishController(SpanishService spanishService) {
        this.spanishService = spanishService;
    }

    // ========================= Spanish Content Management =========================

    /**
     * Create new Spanish content.
     *
     * @param spanishDTO DTO containing Spanish content details.
     * @return Success message.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createSpanishContent(@Valid @RequestBody SpanishRequestDTO spanishDTO) {
        logger.info("Creating Spanish content: {}", spanishDTO.getTitle());
        spanishService.createSpanishContent(spanishDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Spanish content created successfully."));
    }

    /**
     * Get all Spanish content.
     *
     * @return List of SpanishResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<SpanishResponseDTO>> getAllSpanishContent() {
        logger.info("Fetching all Spanish content.");
        return ResponseEntity.ok(spanishService.getAllSpanishContent());
    }

    /**
     * Get specific Spanish content by ID.
     *
     * @param contentId The ID of the Spanish content.
     * @return SpanishResponseDTO containing the details.
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<SpanishResponseDTO> getSpanishContentById(@PathVariable Long contentId) {
        logger.info("Fetching Spanish content with ID: {}", contentId);
        return spanishService.getSpanishContentById(contentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update existing Spanish content.
     *
     * @param contentId The ID of the Spanish content.
     * @param spanishDTO DTO containing updated Spanish content details.
     * @return Success message.
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateSpanishContent(
            @PathVariable Long contentId,
            @Valid @RequestBody SpanishRequestDTO spanishDTO) {
        logger.info("Updating Spanish content with ID: {}", contentId);
        spanishService.updateSpanishContent(contentId, spanishDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Spanish content updated successfully."));
    }

    /**
     * Delete Spanish content by ID.
     *
     * @param contentId The ID of the Spanish content.
     * @return Success message.
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteSpanishContent(@PathVariable Long contentId) {
        logger.info("Deleting Spanish content with ID: {}", contentId);
        spanishService.deleteSpanishContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Spanish content deleted successfully."));
    }

    // ========================= Spanish Exercises =========================

    /**
     * Start a Spanish exercise.
     *
     * @param contentId The ID of the Spanish content.
     * @return Exercise initiation message.
     */
    @PostMapping("/{contentId}/start-exercise")
    public ResponseEntity<ResponseMessageDTO> startExercise(@PathVariable Long contentId) {
        logger.info("Starting exercise for Spanish content ID: {}", contentId);
        spanishService.startExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Spanish exercise started successfully."));
    }

    /**
     * End a Spanish exercise.
     *
     * @param contentId The ID of the Spanish content.
     * @return Exercise conclusion message.
     */
    @PostMapping("/{contentId}/end-exercise")
    public ResponseEntity<ResponseMessageDTO> endExercise(@PathVariable Long contentId) {
        logger.info("Ending exercise for Spanish content ID: {}", contentId);
        spanishService.endExercise(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Spanish exercise ended successfully."));
    }

    /**
     * Add a tag to Spanish content.
     *
     * @param contentId The ID of the Spanish content.
     * @param tag Tag to be added.
     * @return Success message.
     */
    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<ResponseMessageDTO> addTag(@PathVariable Long contentId, @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to Spanish content with ID: {}", tag, contentId);
        spanishService.addTag(contentId, tag);
        return ResponseEntity.ok(new ResponseMessageDTO("Tag added successfully."));
    }
}