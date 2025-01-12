package com.edubot.repository.subjects.science;

import com.edubot.model.subjects.Science;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for Science content.
 */
@Repository
public class ScienceCustomImpl implements ScienceCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Science> findRecentScienceContent(int limit) {
        return entityManager.createQuery(
                "SELECT s FROM Science s ORDER BY s.createdAt DESC", Science.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countScienceContentByProficiencyLevel(String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Science s WHERE s.proficiencyLevel = :level", Long.class)
                .setParameter("level", proficiencyLevel)
                .getSingleResult();
    }

    @Override
    public List<Science> findByFieldOfStudyAndLevel(String fieldOfStudy, String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT s FROM Science s WHERE s.fieldOfStudy = :field AND s.proficiencyLevel = :level", 
                Science.class)
                .setParameter("field", fieldOfStudy)
                .setParameter("level", proficiencyLevel)
                .getResultList();
    }
} 