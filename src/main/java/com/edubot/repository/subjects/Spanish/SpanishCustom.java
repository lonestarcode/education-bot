package com.edubot.repository.subjects.Spanish;

import com.edubot.model.subjects.Spanish;
import java.util.List;

/**
 * Custom repository interface for Spanish-specific complex queries.
 */
public interface SpanishCustom {

    /**
     * Find recent Spanish content based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent Spanish content.
     */
    List<Spanish> findRecentSpanishContent(int limit);

    /**
     * Count Spanish content by proficiency level.
     *
     * @param proficiencyLevel The proficiency level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of Spanish content items matching the proficiency level.
     */
    long countSpanishContentByProficiencyLevel(String proficiencyLevel);

    /**
     * Find Spanish content by language level and proficiency level.
     *
     * @param languageLevel The language level (e.g., A1, A2, B1, B2, C1, C2).
     * @param proficiencyLevel The proficiency level required.
     * @return List of matching Spanish content.
     */
    List<Spanish> findByLanguageLevelAndLevel(String languageLevel, String proficiencyLevel);
} 