package com.edubot.repository.subjects.math;

import com.edubot.model.subjects.Math;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for managing Math content entities.
 */
@Repository
public interface MathRepository extends JpaRepository<Math, Long>, MathCustom {
    
    // ✅ Pagination and Sorting
    Page<Math> findByFocusArea(String focusArea, Pageable pageable);

    Page<Math> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    // ✅ JPQL Query Example
    @Query("SELECT m FROM Math m WHERE m.focusArea = :area AND m.proficiencyLevel = :level")
    Optional<Math> findByFocusAreaAndProficiencyLevel(
        @Param("area") String focusArea, 
        @Param("level") String level
    );

    // ✅ Read-Only Query
    @Transactional(readOnly = true)
    Optional<Math> findById(Long id);
}