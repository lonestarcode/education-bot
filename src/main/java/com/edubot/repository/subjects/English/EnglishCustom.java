package com.edubot.repository.subjects.English;

import com.edubot.model.subject.EnglishContent;

import java.util.List;

/**
 * Custom repository for English subject-specific complex queries.
 */
public interface EnglishCustom {
    
    /**
     * Find recent English topics based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent English content topics.
     */
    List<English> findRecentEnglishTopics(int limit);

    /**
     * Count English topics by difficulty level.
     *
     * @param difficultyLevel The difficulty level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of English topics matching the difficulty.
     */
    long countEnglishTopicsByDifficulty(String difficultyLevel);
}