package com.edubot.controller.lecture;

import com.edubot.dto.LectureRequestDTO;
import com.edubot.dto.LectureResponseDTO;
import com.edubot.dto.ResponseMessageDTO;
import com.edubot.service.LectureService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * LectureController manages lectures and applies lecture rules.
 */
@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // ✅ Create Lecture
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createLecture(@Valid @RequestBody LectureRequestDTO lectureDTO) {
        logger.info("Creating lecture: {}", lectureDTO.getTitle());
        lectureService.createLecture(lectureDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture created successfully."));
    }

    // ✅ Get All Lectures
    @GetMapping
    public ResponseEntity<List<LectureResponseDTO>> getAllLectures() {
        logger.info("Fetching all lectures.");
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    // ✅ Get Lecture by ID
    @GetMapping("/{id}")
    public ResponseEntity<LectureResponseDTO> getLectureById(@PathVariable Long id) {
        logger.info("Fetching lecture with ID: {}", id);
        return ResponseEntity.ok(lectureService.getLectureById(id));
    }

    // ✅ Update Lecture
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> updateLecture(
            @PathVariable Long id,
            @Valid @RequestBody LectureRequestDTO lectureDTO) {
        logger.info("Updating lecture with ID: {}", id);
        lectureService.updateLecture(id, lectureDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture updated successfully."));
    }

    // ✅ Delete Lecture
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteLecture(@PathVariable Long id) {
        logger.info("Deleting lecture with ID: {}", id);
        lectureService.deleteLecture(id);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture deleted successfully."));
    }

    // ✅ Apply Default Rules
    @PostMapping("/{id}/apply-default-rules")
    public ResponseEntity<ResponseMessageDTO> applyDefaultRules(@PathVariable Long id) {
        logger.info("Applying default rules to lecture ID: {}", id);
        lectureService.applyDefaultRules(id);
        return ResponseEntity.ok(new ResponseMessageDTO("Default rules applied successfully."));
    }

    // ✅ Apply Individual Rules
    @PostMapping("/{id}/apply-individual-rules")
    public ResponseEntity<ResponseMessageDTO> applyIndividualRules(
            @PathVariable Long id,
            @RequestParam String ruleKey,
            @RequestParam String ruleValue) {
        logger.info("Applying rule '{}' with value '{}' to lecture {}", ruleKey, ruleValue, id);
        lectureService.applyIndividualRules(id, ruleKey, ruleValue);
        return ResponseEntity.ok(new ResponseMessageDTO("Individual rules applied successfully."));
    }
}