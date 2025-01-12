package com.edubot.repository;

import com.edubot.model.material.StudyMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing study materials across all subjects.
 */
@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    
    /**
     * Find study materials by subject.
     */
    Page<StudyMaterial> findBySubject(String subject, Pageable pageable);

    /**
     * Find study materials by type.
     */
    Page<StudyMaterial> findByMaterialType(String materialType, Pageable pageable);

    /**
     * Find study materials by subject and proficiency level.
     */
    @Query("SELECT sm FROM StudyMaterial sm WHERE sm.subject = :subject AND sm.proficiencyLevel = :level")
    List<StudyMaterial> findBySubjectAndLevel(
        @Param("subject") String subject,
        @Param("level") String proficiencyLevel
    );

    /**
     * Find featured study materials.
     */
    Page<StudyMaterial> findByFeaturedTrue(Pageable pageable);
}
