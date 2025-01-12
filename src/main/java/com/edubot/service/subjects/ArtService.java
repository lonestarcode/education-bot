package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.ArtRequestDTO;
import com.edubot.dto.SUBJECTS.ArtResponseDTO;
import com.edubot.model.subjects.Art;
import com.edubot.repository.subject.ArtRepository;
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
 * Handles business logic for managing Art content, including validation and logging.
 */
@Service
public class ArtService {

    private static final Logger logger = LoggerFactory.getLogger(ArtService.class);

    private final ArtRepository artRepository;

    @Autowired
    public ArtService(ArtRepository artRepository) {
        this.artRepository = artRepository;
    }

    // ========================= Art Content Management =========================

    /**
     * Create new Art content.
     *
     * @param artDTO DTO containing Art content details.
     */
    public void createArtContent(@Valid ArtRequestDTO artDTO) {
        logger.info("Creating Art content: {}", artDTO.getTitle());
        Art artContent = new Art(
                artDTO.getTitle(),
                artDTO.getContentBody(),
                artDTO.getFocusArea(),
                artDTO.getProficiencyLevel()
        );
        artRepository.save(artContent);
        logger.info("Art content '{}' created successfully.", artDTO.getTitle());
    }

    /**
     * Retrieve all Art content.
     *
     * @return List of ArtResponseDTO.
     */
    public List<ArtResponseDTO> getAllArtContent() {
        logger.info("Fetching all Art content.");
        return artRepository.findAll().stream()
                .map(content -> ArtResponseDTO.builder()
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
     * Retrieve specific Art content by ID.
     *
     * @param contentId The ID of the Art content.
     * @return ArtResponseDTO containing details.
     */
    public ArtResponseDTO getArtContentById(@NotNull Long contentId) {
        logger.info("Fetching Art content with ID: {}", contentId);
        Art artContent = artRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Art content not found."));

        return ArtResponseDTO.builder()
                .id(artContent.getId())
                .title(artContent.getTitle())
                .contentBody(artContent.getContentBody())
                .focusArea(artContent.getFocusArea())
                .proficiencyLevel(artContent.getProficiencyLevel())
                .createdAt(artContent.getCreatedAt().toString())
                .updatedAt(artContent.getUpdatedAt().toString())
                .build();
    }

    /**
     * Update existing Art content.
     *
     * @param contentId The ID of the Art content.
     * @param artDTO DTO containing updated Art content details.
     */
    public void updateArtContent(@NotNull Long contentId, @Valid ArtRequestDTO artDTO) {
        logger.info("Updating Art content with ID: {}", contentId);
        Art artContent = artRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Art content not found."));

        artContent.setTitle(artDTO.getTitle());
        artContent.setContentBody(artDTO.getContentBody());
        artContent.setFocusArea(artDTO.getFocusArea());
        artContent.setProficiencyLevel(artDTO.getProficiencyLevel());

        artRepository.save(artContent);
        logger.info("Art content with ID {} updated successfully.", contentId);
    }

    /**
     * Delete Art content by ID.
     *
     * @param contentId The ID of the Art content.
     */
    public void deleteArtContent(@NotNull Long contentId) {
        logger.info("Deleting Art content with ID: {}", contentId);
        if (!artRepository.existsById(contentId)) {
            throw new IllegalArgumentException("Art content not found.");
        }

        artRepository.deleteById(contentId);
        logger.info("Art content with ID {} deleted successfully.", contentId);
    }

    // ========================= Exercises and Tags =========================

    public void startExercise(@NotNull Long contentId) {
        logger.info("Starting exercise for Art content ID: {}", contentId);
        // Business logic for starting exercise
    }

    public void endExercise(@NotNull Long contentId) {
        logger.info("Ending exercise for Art content ID: {}", contentId);
        // Business logic for ending exercise
    }

    public void addTag(@NotNull Long contentId, @NotNull String tag) {
        logger.info("Adding tag '{}' to Art content ID: {}", tag, contentId);
        // Business logic for adding tags
    }
}