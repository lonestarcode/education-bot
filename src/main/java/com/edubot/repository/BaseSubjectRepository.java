package com.edubot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface containing common methods for all subject repositories.
 * @param <T> The subject entity type
 */
@NoRepositoryBean
public interface BaseSubjectRepository<T> extends JpaRepository<T, Long> {
    
    /**
     * Find content by proficiency level with pagination.
     */
    Page<T> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    /**
     * Count content by proficiency level.
     */
    long countByProficiencyLevel(String proficiencyLevel);

    /**
     * Find content created within the last N days.
     */
    Page<T> findByCreatedAtAfter(java.time.LocalDateTime date, Pageable pageable);
}
