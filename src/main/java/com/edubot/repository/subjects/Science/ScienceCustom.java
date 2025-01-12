package com.edubot.repository.subjects.science;

import com.edubot.model.subjects.Science;
import java.util.List;

/**
 * Custom repository interface for Science-specific complex queries.
 */
public interface ScienceCustom {

    /**
     * Find recent Science content based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent Science content.
     */
    List<Science> findRecentScienceContent(int limit);

    /**
     * Count Science content by proficiency level.
     *
     * @param proficiencyLevel The proficiency level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of Science content items matching the proficiency level.
     */
    long countScienceContentByProficiencyLevel(String proficiencyLevel);

    /**
     * Find Science content by field of study and proficiency level.
     *
     * @param fieldOfStudy The field of study of the content.
     * @param proficiencyLevel The proficiency level required.
     * @return List of matching Science content.
     */
    List<Science> findByFieldOfStudyAndLevel(String fieldOfStudy, String proficiencyLevel);
}