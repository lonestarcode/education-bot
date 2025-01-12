package com.edubot.repository.subjects.History;

import com.edubot.model.subjects.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for managing History content entities.
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistoryCustom {
    
    // ✅ Pagination and Sorting
    Page<History> findByTimePeriod(String timePeriod, Pageable pageable);

    Page<History> findByProficiencyLevel(String proficiencyLevel, Pageable pageable);

    // ✅ JPQL Query Example
    @Query("SELECT h FROM History h WHERE h.timePeriod = :period AND h.proficiencyLevel = :level")
    Optional<History> findByTimePeriodAndProficiencyLevel(
        @Param("period") String timePeriod, 
        @Param("level") String level
    );

    // ✅ Read-Only Query
    @Transactional(readOnly = true)
    Optional<History> findById(Long id);
}