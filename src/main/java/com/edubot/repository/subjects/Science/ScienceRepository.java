package com.edubot.repository.subjects.science;

import com.edubot.model.subjects.Science;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for managing Science content entities.
 */
@Repository
public interface ScienceRepository extends JpaRepository<Science, Long>, ScienceCustom {
    
    // ✅ Pagination and Sorting
    Page<Science> findByFieldOfStudy(String fieldOfStudy, Pageable pageable);

    Page<Science> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    // ✅ JPQL Query Example
    @Query("SELECT s FROM Science s WHERE s.fieldOfStudy = :field AND s.proficiencyLevel = :level")
    Optional<Science> findByFieldOfStudyAndProficiencyLevel(
        @Param("field") String fieldOfStudy, 
        @Param("level") String level
    );

    // ✅ Read-Only Query
    @Transactional(readOnly = true)
    Optional<Science> findById(Long id);
}