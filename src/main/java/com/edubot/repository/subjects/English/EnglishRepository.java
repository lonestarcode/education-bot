package com.edubot.repository.subjects.English;

import com.edubot.model.subject.EnglishContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing English subject content.
 * Includes basic CRUD operations and derived queries.
 */
@Repository
public interface EnglishRepository extends JpaRepository<English, Long>, EnglishRepositoryCustom {
    
    /**
     * Find English content by topic with pagination.
     *
     * @param topic The topic of the content.
     * @param pageable Pagination details.
     * @return A page of EnglishContent matching the topic.
     */
    Page<English> findByTopic(String topic, Pageable pageable);
}