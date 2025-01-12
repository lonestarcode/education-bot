package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.EnglishRequestDTO;
import com.edubot.dto.SUBJECTS.EnglishResponseDTO;
import com.edubot.model.subjects.English;
import com.edubot.repository.subject.EnglishRepository;
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
 * Handles business logic for managing English content, including validation and logging.
 */
@Service
public class EnglishService {

    private static final Logger logger = LoggerFactory.getLogger(EnglishService.class);

    private final EnglishRepository englishRepository;

    @Autowired
    public EnglishService(EnglishRepository englishRepository) {
        this.englishRepository = englishRepository;
    }

    // ========================= English Content Management =========================

    /**
     * Create new English content.
     *
     * @param englishDTO DTO containing English content details.
     */
    public void createEnglishContent(@Valid EnglishRequestDTO englishDTO) {
        logger.info("Creating English content: {}", englishDTO.getTitle());
        English englishContent = new English(
                englishDTO.getTitle(),
                englishDTO.getDescription(),
                englishDTO.getContentBody()
        );
        englishRepository.save(englishContent);
        logger.info("English content '{}' created successfully.", englishDTO.getTitle());
    }

    /**
     * Retrieve all English content.
     *
     * @return List of EnglishResponseDTO.
     */
    public List<EnglishResponseDTO> getAllEnglishContent() {
        logger.info("Fetching all English content.");
        return englishRepository.findAll().stream()
                .map(content -> new EnglishResponseDTO(
                        content.getId(),
                        content.getTitle(),
                        content.getDescription(),
                        content.getContentBody()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve specific English content by ID.
     *
     * @param contentId The ID of the English content.
     * @return EnglishResponseDTO containing details.
     */
    public EnglishResponseDTO getEnglishContentById(@NotNull Long contentId) {
        logger.info("Fetching English content with ID: {}", contentId);
        Optional<English> content = englishRepository.findById(contentId);

        if (content.isEmpty()) {
            logger.warn("English content with ID {} not found.", contentId);
            throw new IllegalArgumentException("English content not found.");
        }

        English englishContent = content.get();
        return new EnglishResponseDTO(
                englishContent.getId(),
                englishContent.getTitle(),
                englishContent.getDescription(),
                englishContent.getContentBody()
        );
    }

    /**
     * Update existing English content.
     *
     * @param contentId The ID of the English content.
     * @param englishDTO DTO containing updated English content details.
     */
    public void updateEnglishContent(@NotNull Long contentId, @Valid EnglishRequestDTO englishDTO) {
        logger.info("Updating English content with ID: {}", contentId);
        English englishContent = englishRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("English content not found."));

        englishContent.setTitle(englishDTO.getTitle());
        englishContent.setDescription(englishDTO.getDescription());
        englishContent.setContentBody(englishDTO.getContentBody());

        englishRepository.save(englishContent);
        logger.info("English content with ID {} updated successfully.", contentId);
    }

    /**
     * Delete English content by ID.
     *
     * @param contentId The ID of the English content.
     */
    public void deleteEnglishContent(@NotNull Long contentId) {
        logger.info("Deleting English content with ID: {}", contentId);
        if (!englishRepository.existsById(contentId)) {
            throw new IllegalArgumentException("English content not found.");
        }

        englishRepository.deleteById(contentId);
        logger.info("English content with ID {} deleted successfully.", contentId);
    }
}