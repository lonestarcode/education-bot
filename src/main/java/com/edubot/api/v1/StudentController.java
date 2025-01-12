package com.edubot.controller.student;

import com.edubot.dto.StudentRequestDTO;
import com.edubot.dto.StudentActivityResponseDTO;
import com.edubot.dto.StudyToolRequestDTO;
import com.edubot.dto.ResponseMessageDTO;
import com.edubot.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Manages student profiles, activities, and AI interactions.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ========================= STUDENT MANAGEMENT =========================

    /**
     * Add a new student profile.
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> addStudentProfile(@Valid @RequestBody StudentRequestDTO studentDTO) {
        logger.info("Adding student profile: {}", studentDTO.getName());
        studentService.addStudentProfile(studentDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Student profile added successfully."));
    }

    /**
     * Remove a student profile by ID.
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResponseMessageDTO> removeStudentProfile(@PathVariable Long studentId) {
        logger.info("Removing student profile: {}", studentId);
        studentService.removeStudentProfile(studentId);
        return ResponseEntity.ok(new ResponseMessageDTO("Student profile removed successfully."));
    }

    /**
     * Retrieve activity log for a student.
     */
    @GetMapping("/{studentId}/activities")
    public ResponseEntity<List<StudentActivityResponseDTO>> getActivityLog(@PathVariable Long studentId) {
        logger.info("Fetching activity log for student ID: {}", studentId);
        return ResponseEntity.ok(studentService.getActivityLog(studentId));
    }

    // ========================= AI INTERACTION =========================

    /**
     * Submit a question for AI assistance.
     */
    @PostMapping("/{studentId}/ask")
    public ResponseEntity<ResponseMessageDTO> askQuestion(
            @PathVariable Long studentId,
            @RequestParam String question) {
        logger.info("Student ID {} asked a question: {}", studentId, question);
        studentService.askQuestion(studentId, question);
        return ResponseEntity.ok(new ResponseMessageDTO("Question submitted successfully."));
    }

    /**
     * Request a homework hint.
     */
    @PostMapping("/{studentId}/homework")
    public ResponseEntity<ResponseMessageDTO> getHomeworkHint(
            @PathVariable Long studentId,
            @RequestParam String homeworkQuestion) {
        logger.info("Homework hint requested by Student ID {}: {}", studentId, homeworkQuestion);
        studentService.getHomeworkHint(studentId, homeworkQuestion);
        return ResponseEntity.ok(new ResponseMessageDTO("Homework hint generated successfully."));
    }

    /**
     * Request an AI-powered study tool.
     */
    @PostMapping("/{studentId}/study-tool")
    public ResponseEntity<ResponseMessageDTO> requestStudyTool(
            @Valid @RequestBody StudyToolRequestDTO studyToolDTO) {
        logger.info("Study tool requested by Student ID {}: Subject - {}, Tool - {}", 
                    studyToolDTO.getStudentId(), studyToolDTO.getSubject(), studyToolDTO.getToolType());
        studentService.requestStudyTool(studyToolDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Study tool generated successfully."));
    }
}