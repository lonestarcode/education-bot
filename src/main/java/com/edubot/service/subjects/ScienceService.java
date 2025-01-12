package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.ScienceRequestDTO;
import com.edubot.dto.SUBJECTS.ScienceResponseDTO;
import com.edubot.model.subjects.Science;
import com.edubot.repository.subject.ScienceRepository;
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
 * Handles business logic for managing Science content, including validation and logging.
 */
@Service
public class ScienceService {

    private static final Logger logger = LoggerFactory.getLogger(ScienceService.class);

    private final ScienceRepository scienceRepository;

    @Autowired
    public ScienceService(ScienceRepository scienceRepository) {
        this.scienceRepository = scienceRepository;
    }

    // ========================= Science Content Management =========================

    /**
     * Create new Science content.
     *
     * @param scienceDTO DTO containing Science content details.
     */
    public void createScienceContent(@Valid ScienceRequestDTO scienceDTO) {
        logger.info("Creating Science content: {}", scienceDTO.getTitle());
        Science scienceContent = new Science(
                scienceDTO.getTitle(),
                scienceDTO.getContentBody(),
                scienceDTO.getFocusArea(),
                scienceDTO.getProficiencyLevel()
        );
        scienceRepository.save(scienceContent);
        logger.info("Science content '{}' created successfully.", scienceDTO.getTitle());
    }

    /**
     * Retrieve all Science content.
     *
     * @return List of ScienceResponseDTO.
     */
    public List<ScienceResponseDTO> getAllScienceContent() {
        logger.info("Fetching all Science content.");
        return scienceRepository.findAll().stream()
                .map(content -> ScienceResponseDTO.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .contentBody(content.getContentBody())
                        .focusArea(content.getFieldOfStudy())
                        .proficiencyLevel(content.getProficiencyLevel())
                        .createdAt(content.getCreatedAt().toString())
                        .updatedAt(content.getUpdatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retrieve specific Science content by ID.
     *
     * @param contentId The ID of the Science content.
     * @return ScienceResponseDTO containing details.
     */
    public ScienceResponseDTO getScienceContentById(@NotNull Long contentId) {
        logger.info("Fetching Science content with ID: {}", contentId);
        Science scienceContent = scienceRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Science content not found."));

        return ScienceResponseDTO.builder()
                .id(scienceContent.getId())
                .title(scienceContent.getTitle())
                .contentBody(scienceContent.getContentBody())
                .focusArea(scienceContent.getFieldOfStudy())
                .proficiencyLevel(scienceContent.getProficiencyLevel())
                .createdAt(scienceContent.getCreatedAt().toString())
                .updatedAt(scienceContent.getUpdatedAt().toString())
                .build();
    }

    /**
     * Update existing Science content.
     *
     * @param contentId The ID of the Science content.
     * @param scienceDTO DTO containing updated Science content details.
     */
    public void updateScienceContent(@NotNull Long contentId, @Valid ScienceRequestDTO scienceDTO) {
        logger.info("Updating Science content with ID: {}", contentId);
        Science scienceContent = scienceRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Science content not found."));

        scienceContent.setTitle(scienceDTO.getTitle());
        scienceContent.setContentBody(scienceDTO.getContentBody());
        scienceContent.setFieldOfStudy(scienceDTO.getFocusArea());
        scienceContent.setProficiencyLevel(scienceDTO.getProficiencyLevel());

        scienceRepository.save(scienceContent);
        logger.info("Science content with ID {} updated successfully.", contentId);
    }

    /**
     * Delete Science content by ID.
     *
     * @param contentId The ID of the Science content.
     */
    public void deleteScienceContent(@NotNull Long contentId) {
        logger.info("Deleting Science content with ID: {}", contentId);
        if (!scienceRepository.existsById(contentId)) {
            throw new IllegalArgumentException("Science content not found.");
        }

        scienceRepository.deleteById(contentId);
        logger.info("Science content with ID {} deleted successfully.", contentId);
    }
}