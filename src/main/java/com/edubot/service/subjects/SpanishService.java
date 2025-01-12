package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.SpanishRequestDTO;
import com.edubot.dto.SUBJECTS.SpanishResponseDTO;
import com.edubot.model.subjects.Spanish;
import com.edubot.repository.subject.SpanishRepository;
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
 * Handles business logic for managing Spanish content, including validation and logging.
 */
@Service
public class SpanishService {

    private static final Logger logger = LoggerFactory.getLogger(SpanishService.class);

    private final SpanishRepository spanishRepository;

    @Autowired
    public SpanishService(SpanishRepository spanishRepository) {
        this.spanishRepository = spanishRepository;
    }

    // ========================= Spanish Content Management =========================

    public void createSpanishContent(@Valid SpanishRequestDTO spanishDTO) {
        logger.info("Creating Spanish content: {}", spanishDTO.getTitle());
        Spanish spanishContent = new Spanish(
                spanishDTO.getTitle(),
                spanishDTO.getDescription(),
                spanishDTO.getContentBody()
        );
        spanishRepository.save(spanishContent);
        logger.info("Spanish content '{}' created successfully.", spanishDTO.getTitle());
    }

    public List<SpanishResponseDTO> getAllSpanishContent() {
        logger.info("Fetching all Spanish content.");
        return spanishRepository.findAll().stream()
                .map(content -> new SpanishResponseDTO(
                        content.getId(),
                        content.getTitle(),
                        content.getDescription(),
                        content.getContentBody()
                ))
                .collect(Collectors.toList());
    }

    public SpanishResponseDTO getSpanishContentById(@NotNull Long contentId) {
        logger.info("Fetching Spanish content with ID: {}", contentId);
        Optional<Spanish> content = spanishRepository.findById(contentId);

        if (content.isEmpty()) {
            logger.warn("Spanish content with ID {} not found.", contentId);
            throw new IllegalArgumentException("Spanish content not found.");
        }

        Spanish spanishContent = content.get();
        return new SpanishResponseDTO(
                spanishContent.getId(),
                spanishContent.getTitle(),
                spanishContent.getDescription(),
                spanishContent.getContentBody()
        );
    }

    public void updateSpanishContent(@NotNull Long contentId, @Valid SpanishRequestDTO spanishDTO) {
        logger.info("Updating Spanish content with ID: {}", contentId);
        Spanish spanishContent = spanishRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Spanish content not found."));

        spanishContent.setTitle(spanishDTO.getTitle());
        spanishContent.setDescription(spanishDTO.getDescription());
        spanishContent.setContentBody(spanishDTO.getContentBody());

        spanishRepository.save(spanishContent);
        logger.info("Spanish content with ID {} updated successfully.", contentId);
    }

    public void deleteSpanishContent(@NotNull Long contentId) {
        logger.info("Deleting Spanish content with ID: {}", contentId);
        if (!spanishRepository.existsById(contentId)) {
            throw new IllegalArgumentException("Spanish content not found.");
        }

        spanishRepository.deleteById(contentId);
        logger.info("Spanish content with ID {} deleted successfully.", contentId);
    }
}