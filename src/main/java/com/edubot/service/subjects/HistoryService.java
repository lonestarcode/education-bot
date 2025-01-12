package com.edubot.service.subjects;

import com.edubot.dto.SUBJECTS.HistoryRequestDTO;
import com.edubot.dto.SUBJECTS.HistoryResponseDTO;
import com.edubot.model.subjects.History;
import com.edubot.repository.subject.HistoryRepository;
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
 * Handles business logic for managing History content, including validation and logging.
 */
@Service
public class HistoryService {

    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    // ========================= History Content Management =========================

    /**
     * Create new History content.
     *
     * @param historyDTO DTO containing History content details.
     */
    public void createHistoryContent(@Valid HistoryRequestDTO historyDTO) {
        logger.info("Creating History content: {}", historyDTO.getTitle());
        History historyContent = new History(
                historyDTO.getTitle(),
                historyDTO.getDescription(),
                historyDTO.getContentBody()
        );
        historyRepository.save(historyContent);
        logger.info("History content '{}' created successfully.", historyDTO.getTitle());
    }

    /**
     * Retrieve all History content.
     *
     * @return List of HistoryResponseDTO.
     */
    public List<HistoryResponseDTO> getAllHistoryContent() {
        logger.info("Fetching all History content.");
        return historyRepository.findAll().stream()
                .map(content -> new HistoryResponseDTO(
                        content.getId(),
                        content.getTitle(),
                        content.getDescription(),
                        content.getContentBody()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve specific History content by ID.
     *
     * @param contentId The ID of the History content.
     * @return HistoryResponseDTO containing details.
     */
    public HistoryResponseDTO getHistoryContentById(@NotNull Long contentId) {
        logger.info("Fetching History content with ID: {}", contentId);
        Optional<History> content = historyRepository.findById(contentId);

        if (content.isEmpty()) {
            logger.warn("History content with ID {} not found.", contentId);
            throw new IllegalArgumentException("History content not found.");
        }

        History historyContent = content.get();
        return new HistoryResponseDTO(
                historyContent.getId(),
                historyContent.getTitle(),
                historyContent.getDescription(),
                historyContent.getContentBody()
        );
    }

    /**
     * Update existing History content.
     *
     * @param contentId The ID of the History content.
     * @param historyDTO DTO containing updated History content details.
     */
    public void updateHistoryContent(@NotNull Long contentId, @Valid HistoryRequestDTO historyDTO) {
        logger.info("Updating History content with ID: {}", contentId);
        History historyContent = historyRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("History content not found."));

        historyContent.setTitle(historyDTO.getTitle());
        historyContent.setDescription(historyDTO.getDescription());
        historyContent.setContentBody(historyDTO.getContentBody());

        historyRepository.save(historyContent);
        logger.info("History content with ID {} updated successfully.", contentId);
    }

    /**
     * Delete History content by ID.
     *
     * @param contentId The ID of the History content.
     */
    public void deleteHistoryContent(@NotNull Long contentId) {
        logger.info("Deleting History content with ID: {}", contentId);
        if (!historyRepository.existsById(contentId)) {
            throw new IllegalArgumentException("History content not found.");
        }

        historyRepository.deleteById(contentId);
        logger.info("History content with ID {} deleted successfully.", contentId);
    }
}