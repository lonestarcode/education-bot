package com.edubot.repository.subjects.History;

import com.edubot.model.subjects.History;
import java.util.List;

/**
 * Custom repository interface for History-specific complex queries.
 */
public interface HistoryCustom {

    /**
     * Find recent History content based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent History content.
     */
    List<History> findRecentHistoryContent(int limit);

    /**
     * Count History content by proficiency level.
     *
     * @param proficiencyLevel The proficiency level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of History content items matching the proficiency level.
     */
    long countHistoryContentByProficiencyLevel(String proficiencyLevel);

    /**
     * Find History content by time period and proficiency level.
     *
     * @param timePeriod The time period of the content.
     * @param proficiencyLevel The proficiency level required.
     * @return List of matching History content.
     */
    List<History> findByTimePeriodAndLevel(String timePeriod, String proficiencyLevel);
} 