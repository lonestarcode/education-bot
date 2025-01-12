package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.MathRequestDTO;
import com.edubot.dto.SUBJECTS.MathResponseDTO;
import com.edubot.model.subjects.Math;
import com.edubot.repository.subject.MathRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles business logic for managing Math content, including validation and logging.
 */
@Service
public class MathService {

    private static final Logger logger = LoggerFactory.getLogger(MathService.class);

    private final MathRepository mathRepository;

    @Autowired
    public MathService(MathRepository mathRepository) {
        this.mathRepository = mathRepository;
    }

    // ========================= Math Content Management =========================

    /**
     * Create new Math content.
     *
     * @param mathDTO DTO containing Math content details.
     */
    public void createMathContent(@Valid MathRequestDTO mathDTO) {
        logger.info("Creating Math content: {}", mathDTO.getTitle());
        Math mathContent = new Math(
                mathDTO.getTitle(),
                mathDTO.getContentBody(),
                mathDTO.getFocusArea(),
                mathDTO.getProficiencyLevel()
        );
        mathRepository.save(mathContent);
        logger.info("Math content '{}' created successfully.", mathDTO.getTitle());
    }

    /**
     * Retrieve all Math content.
     *
     * @return List of MathResponseDTO.
     */
    public List<MathResponseDTO> getAllMathContent() {
        logger.info("Fetching all Math content.");
        return mathRepository.findAll().stream()
                .map(content -> MathResponseDTO.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .contentBody(content.getContentBody())
                        .focusArea(content.getFocusArea())
                        .proficiencyLevel(content.getProficiencyLevel())
                        .createdAt(content.getCreatedAt().toString())
                        .updatedAt(content.getUpdatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retrieve specific Math content by ID.
     *
     * @param contentId The ID of the Math content.
     * @return MathResponseDTO containing details.
     */
    public MathResponseDTO getMathContentById(@NotNull Long contentId) {
        logger.info("Fetching Math content with ID: {}", contentId);
        Math mathContent = mathRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Math content not found."));

        return MathResponseDTO.builder()
                .id(mathContent.getId())
                .title(mathContent.getTitle())
                .contentBody(mathContent.getContentBody())
                .focusArea(mathContent.getFocusArea())
                .proficiencyLevel(mathContent.getProficiencyLevel())
                .createdAt(mathContent.getCreatedAt().toString())
                .updatedAt(mathContent.getUpdatedAt().toString())
                .build();
    }

    /**
     * Update existing Math content.
     *
     * @param contentId The ID of the Math content.
     * @param mathDTO DTO containing updated Math content details.
     */
    public void updateMathContent(@NotNull Long contentId, @Valid MathRequestDTO mathDTO) {
        logger.info("Updating Math content with ID: {}", contentId);
        Math mathContent = mathRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Math content not found."));

        mathContent.setTitle(mathDTO.getTitle());
        mathContent.setContentBody(mathDTO.getContentBody());
        mathContent.setFocusArea(mathDTO.getFocusArea());
        mathContent.setProficiencyLevel(mathDTO.getProficiencyLevel());

        mathRepository.save(mathContent);
        logger.info("Math content with ID {} updated successfully.", contentId);
    }

    /**
     * Delete Math content by ID.
     *
     * @param contentId The ID of the Math content.
     */
    public void deleteMathContent(@NotNull Long contentId) {
        logger.info("Deleting Math content with ID: {}", contentId);
        if (!mathRepository.existsById(contentId)) {
            throw new IllegalArgumentException("Math content not found.");
        }

        mathRepository.deleteById(contentId);
        logger.info("Math content with ID {} deleted successfully.", contentId);
    }
}