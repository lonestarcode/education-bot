package com.edubot.repository.subjects.math;

import com.edubot.model.subjects.Math;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for Math content.
 */
@Repository
public class MathCustomImpl implements MathCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Math> findRecentMathContent(int limit) {
        return entityManager.createQuery(
                "SELECT m FROM Math m ORDER BY m.createdAt DESC", Math.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countMathContentByProficiencyLevel(String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(m) FROM Math m WHERE m.proficiencyLevel = :level", Long.class)
                .setParameter("level", proficiencyLevel)
                .getSingleResult();
    }

    @Override
    public List<Math> findByFocusAreaAndLevel(String focusArea, String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT m FROM Math m WHERE m.focusArea = :area AND m.proficiencyLevel = :level", 
                Math.class)
                .setParameter("area", focusArea)
                .setParameter("level", proficiencyLevel)
                .getResultList();
    }
} 