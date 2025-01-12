package com.edubot.repository.subjects.Art;

import com.edubot.model.subjects.Art;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for managing Art content entities.
 */
@Repository
public interface ArtRepository extends JpaRepository<Art, Long>, ArtCustom {
    
    // ✅ Pagination and Sorting
    Page<Art> findByFocusArea(String focusArea, Pageable pageable);

    Page<Art> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    // ✅ JPQL Query Example
    @Query("SELECT a FROM Art a WHERE a.focusArea = :area AND a.proficiencyLevel = :level")
    Optional<Art> findByFocusAreaAndProficiencyLevel(
        @Param("area") String focusArea, 
        @Param("level") String level
    );

    // ✅ Read-Only Query
    @Transactional(readOnly = true)
    Optional<Art> findById(Long id);
}