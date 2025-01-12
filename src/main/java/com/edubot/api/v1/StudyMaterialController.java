package com.edubot.controller.content;

import com.edubot.dto.StudyMaterialRequestDTO;
import com.edubot.dto.StudyMaterialResponseDTO;
import com.edubot.service.StudyMaterialService;
import com.edubot.dto.ResponseMessageDTO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * StudyMaterialController manages CRUD operations for study materials.
 */
@RestController
@RequestMapping("/api/materials")
public class StudyMaterialController {

    private static final Logger logger = LoggerFactory.getLogger(StudyMaterialController.class);
    private final StudyMaterialService studyMaterialService;

    @Autowired
    public StudyMaterialController(StudyMaterialService studyMaterialService) {
        this.studyMaterialService = studyMaterialService;
    }

    // ✅ Add Study Material
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> addMaterial(@Valid @RequestBody StudyMaterialRequestDTO materialDTO) {
        logger.info("Adding study material: {}", materialDTO.getTitle());
        studyMaterialService.addMaterial(materialDTO);
        return ResponseEntity.ok(new ResponseMessageDTO("Material added successfully."));
    }

    // ✅ Retrieve All Materials
    @GetMapping
    public ResponseEntity<List<StudyMaterialResponseDTO>> getAllMaterials() {
        logger.info("Fetching all study materials.");
        return ResponseEntity.ok(studyMaterialService.getAllMaterials());
    }

    // ✅ Retrieve by Title
    @GetMapping("/{title}")
    public ResponseEntity<StudyMaterialResponseDTO> getMaterialByTitle(@PathVariable String title) {
        logger.info("Fetching material by title: {}", title);
        return ResponseEntity.ok(studyMaterialService.getMaterialByTitle(title));
    }
}