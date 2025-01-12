package com.edubot.repository.subjects.Spanish;

import com.edubot.model.subjects.Spanish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for managing Spanish content entities.
 */
@Repository
public interface SpanishRepository extends JpaRepository<Spanish, Long>, SpanishCustom {
    
    // ✅ Pagination and Sorting
    Page<Spanish> findByLanguageLevel(String languageLevel, Pageable pageable);

    Page<Spanish> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    // ✅ JPQL Query Example
    @Query("SELECT s FROM Spanish s WHERE s.languageLevel = :level AND s.proficiencyLevel = :prof")
    Optional<Spanish> findByLanguageLevelAndProficiencyLevel(
        @Param("level") String languageLevel, 
        @Param("prof") String proficiencyLevel
    );

    // ✅ Read-Only Query
    @Transactional(readOnly = true)
    Optional<Spanish> findById(Long id);
}