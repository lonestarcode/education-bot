package com.edubot.repository.subjects.Art;

import com.edubot.model.subjects.Art;
import java.util.List;

/**
 * Custom repository interface for Art-specific complex queries.
 */
public interface ArtCustom {

    /**
     * Find recent Art content based on creation date.
     *
     * @param limit Maximum number of results.
     * @return A list of recent Art content.
     */
    List<Art> findRecentArtContent(int limit);

    /**
     * Count Art content by proficiency level.
     *
     * @param proficiencyLevel The proficiency level (e.g., Beginner, Intermediate, Advanced).
     * @return Number of Art content items matching the proficiency level.
     */
    long countArtContentByProficiencyLevel(String proficiencyLevel);

    /**
     * Find Art content by focus area and proficiency level.
     *
     * @param focusArea The focus area of the content.
     * @param proficiencyLevel The proficiency level required.
     * @return List of matching Art content.
     */
    List<Art> findByFocusAreaAndLevel(String focusArea, String proficiencyLevel);
} 