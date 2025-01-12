package com.edubot.controller.teacher;

import com.edubot.dto.LectureRequestDTO;
import com.edubot.dto.ResponseMessageDTO;
import com.edubot.dto.LectureResponseDTO;
import com.edubot.dto.AnalyticsResponseDTO;
import com.edubot.dto.StudyMaterialRequestDTO;
import com.edubot.service.TeacherService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Manages teacher-specific operations, including lecture scheduling and study material uploads.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // ========================= LECTURE MANAGEMENT =========================

    /**
     * Schedule a lecture.
     */
    @PostMapping("/lectures/schedule")
    public ResponseEntity<ResponseMessageDTO> scheduleLecture(@Valid @RequestBody LectureRequestDTO lectureDTO) {
        logger.info("Scheduling lecture: {}", lectureDTO.getTitle());
        teacherService.scheduleLecture(lectureDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture scheduled successfully."));
    }

    /**
     * Get lectures by subject and topic.
     */
    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponseDTO>> getLecturesByTopic(
            @RequestParam String subject, @RequestParam String topic) {
        logger.info("Fetching lectures for subject '{}' and topic '{}'", subject, topic);
        return ResponseEntity.ok(teacherService.getLecturesByTopic(subject, topic));
    }

    /**
     * Update lecture details.
     */
    @PutMapping("/lectures/{id}")
    public ResponseEntity<ResponseMessageDTO> updateLecture(
            @PathVariable Long id,
            @Valid @RequestBody LectureRequestDTO lectureDTO) {
        logger.info("Updating lecture with ID: {}", id);
        teacherService.updateLecture(id, lectureDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture updated successfully."));
    }

    /**
     * Delete a lecture.
     */
    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteLecture(@PathVariable Long id) {
        logger.info("Deleting lecture with ID: {}", id);
        teacherService.deleteLecture(id);
        return ResponseEntity.ok(new ResponseMessageDTO("Lecture deleted successfully."));
    }

    // ========================= STUDY MATERIAL MANAGEMENT =========================

    /**
     * Upload and activate study material.
     */
    @PostMapping("/study-material/upload")
    public ResponseEntity<ResponseMessageDTO> uploadAndActivateMaterial(@Valid @RequestBody StudyMaterialRequestDTO materialDTO) {
        logger.info("Uploading study material: {}", materialDTO.getTitle());
        teacherService.uploadAndActivateMaterial(materialDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Study material uploaded successfully."));
    }

    /**
     * Get analytics report for study material.
     */
    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponseDTO> getAnalytics(@RequestParam Long lectureId) {
        logger.info("Fetching analytics for lecture ID: {}", lectureId);
        return ResponseEntity.ok(teacherService.getAnalyticsReport(lectureId));
    }
}