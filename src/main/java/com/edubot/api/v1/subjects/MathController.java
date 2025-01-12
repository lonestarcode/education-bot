package com.edubot.api.v1.subjects;

import com.edubot.dto.SUBJECTS.MathRequestDTO;
import com.edubot.dto.SUBJECTS.MathResponseDTO;
import com.edubot.service.subject.MathService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to math lectures, exercises, and content management.
 */
@RestController
@RequestMapping("/api/math")
public class MathController {

    private static final Logger logger = LoggerFactory.getLogger(MathController.class);

    private final MathService mathService;

    @Autowired
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    // ========================= Math Content Management =========================

    /**
     * Create new math content.
     *
     * @param mathDTO DTO containing math content details.
     * @return Success message.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createMathContent(@Valid @RequestBody MathRequestDTO mathDTO) {
        logger.info("Creating math content: {}", mathDTO.getTitle());
        mathService.createMathContent(mathDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Math content created successfully."));
    }

    /**
     * Get all math content.
     *
     * @return List of MathResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<MathResponseDTO>> getAllMathContent() {
        logger.info("Fetching all math content.");
        return ResponseEntity.ok(mathService.getAllMathContent());
    }

    /**
     * Get specific math content by ID.
     *
     * @param contentId The ID of the math content.
     * @return MathResponseDTO containing the details.
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<MathResponseDTO> getMathContentById(@PathVariable Long contentId) {
        logger.info("Fetching math content with ID: {}", contentId);
        MathResponseDTO response = mathService.getMathContentById(contentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Update existing math content.
     *
     * @param contentId The ID of the math content.
     * @param mathDTO DTO containing updated math content details.
     * @return Success message.
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateMathContent(
            @PathVariable Long contentId,
            @Valid @RequestBody MathRequestDTO mathDTO) {
        logger.info("Updating math content with ID: {}", contentId);
        mathService.updateMathContent(contentId, mathDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Math content updated successfully."));
    }

    /**
     * Delete math content by ID.
     *
     * @param contentId The ID of the math content.
     * @return Success message.
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteMathContent(@PathVariable Long contentId) {
        logger.info("Deleting math content with ID: {}", contentId);
        mathService.deleteMathContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Math content deleted successfully."));
    }

    // ========================= Math Exercises =========================

    /**
     * Start a math exercise.
     *
     * @param contentId The ID of the math content.
     * @return Exercise initiation message.
     */
    @PostMapping("/{contentId}/start-exercise")
    public ResponseEntity<String> startExercise(@PathVariable Long contentId) {
        logger.info("Starting exercise for math content ID: {}", contentId);
        mathService.startExercise(contentId);
        return ResponseEntity.ok("Math exercise started successfully.");
    }

    /**
     * End a math exercise.
     *
     * @param contentId The ID of the math content.
     * @return Exercise conclusion message.
     */
    @PostMapping("/{contentId}/end-exercise")
    public ResponseEntity<String> endExercise(@PathVariable Long contentId) {
        logger.info("Ending exercise for math content ID: {}", contentId);
        mathService.endExercise(contentId);
        return ResponseEntity.ok("Math exercise ended successfully.");
    }

    /**
     * Add a tag to math content.
     *
     * @param contentId The ID of the math content.
     * @param tag Tag to be added.
     * @return Success message.
     */
    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<String> addTag(@PathVariable Long contentId, @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to math content with ID: {}", tag, contentId);
        mathService.addTag(contentId, tag);
        return ResponseEntity.ok("Tag added successfully.");
    }
}