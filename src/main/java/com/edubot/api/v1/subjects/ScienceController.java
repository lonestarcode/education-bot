package com.edubot.api.v1.subjects;

import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.SUBJECTS.ScienceRequestDTO;
import com.edubot.dto.SUBJECTS.ScienceResponseDTO;
import com.edubot.service.subject.ScienceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles operations related to science lectures, experiments, and content management.
 */
@RestController
@RequestMapping("/api/science")
public class ScienceController {

    private static final Logger logger = LoggerFactory.getLogger(ScienceController.class);

    private final ScienceService scienceService;

    @Autowired
    public ScienceController(ScienceService scienceService) {
        this.scienceService = scienceService;
    }

    // ========================= Science Content Management =========================

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createScienceContent(@Valid @RequestBody ScienceRequestDTO scienceDTO) {
        logger.info("Creating science content: {}", scienceDTO.getTitle());
        scienceService.createScienceContent(scienceDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Science content created successfully."));
    }

    @GetMapping
    public ResponseEntity<List<ScienceResponseDTO>> getAllScienceContent() {
        logger.info("Fetching all science content.");
        return ResponseEntity.ok(scienceService.getAllScienceContent());
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ScienceResponseDTO> getScienceContentById(@PathVariable Long contentId) {
        logger.info("Fetching science content with ID: {}", contentId);
        ScienceResponseDTO response = scienceService.getScienceContentById(contentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> updateScienceContent(
            @PathVariable Long contentId,
            @Valid @RequestBody ScienceRequestDTO scienceDTO) {
        logger.info("Updating science content with ID: {}", contentId);
        scienceService.updateScienceContent(contentId, scienceDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Science content updated successfully."));
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<ResponseMessageDTO> deleteScienceContent(@PathVariable Long contentId) {
        logger.info("Deleting science content with ID: {}", contentId);
        scienceService.deleteScienceContent(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Science content deleted successfully."));
    }

    // ========================= Science Experiments =========================

    @PostMapping("/{contentId}/start-experiment")
    public ResponseEntity<ResponseMessageDTO> startExperiment(@PathVariable Long contentId) {
        logger.info("Starting experiment for science content ID: {}", contentId);
        scienceService.startExperiment(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Science experiment started successfully."));
    }

    @PostMapping("/{contentId}/end-experiment")
    public ResponseEntity<ResponseMessageDTO> endExperiment(@PathVariable Long contentId) {
        logger.info("Ending experiment for science content ID: {}", contentId);
        scienceService.endExperiment(contentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Science experiment ended successfully."));
    }

    @PostMapping("/{contentId}/add-tag")
    public ResponseEntity<ResponseMessageDTO> addTag(@PathVariable Long contentId, @RequestParam @NotNull String tag) {
        logger.info("Adding tag '{}' to science content with ID: {}", tag, contentId);
        scienceService.addTag(contentId, tag);
        return ResponseEntity.ok(new ResponseMessageDTO("Tag added successfully."));
    }
}