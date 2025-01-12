package com.edubot.repository.subjects.math;

import com.edubot.model.subjects.Math;
import java.util.List;

/**
 * Custom repository interface for Math-specific complex queries.
 */
public interface MathCustom {

    /**
     * Find recent Math content based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent Math content.
     */
    List<Math> findRecentMathContent(int limit);

    /**
     * Count Math content by proficiency level.
     *
     * @param proficiencyLevel The proficiency level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of Math content items matching the proficiency level.
     */
    long countMathContentByProficiencyLevel(String proficiencyLevel);

    /**
     * Find Math content by focus area and proficiency level.
     *
     * @param focusArea The focus area of the content.
     * @param proficiencyLevel The proficiency level required.
     * @return List of matching Math content.
     */
    List<Math> findByFocusAreaAndLevel(String focusArea, String proficiencyLevel);
}