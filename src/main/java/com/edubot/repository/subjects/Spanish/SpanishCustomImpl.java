package com.edubot.repository.subjects.Spanish;

import com.edubot.model.subjects.Spanish;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for Spanish content.
 */
@Repository
public class SpanishCustomImpl implements SpanishCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Spanish> findRecentSpanishContent(int limit) {
        return entityManager.createQuery(
                "SELECT s FROM Spanish s ORDER BY s.createdAt DESC", Spanish.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countSpanishContentByProficiencyLevel(String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Spanish s WHERE s.proficiencyLevel = :level", Long.class)
                .setParameter("level", proficiencyLevel)
                .getSingleResult();
    }

    @Override
    public List<Spanish> findByLanguageLevelAndLevel(String languageLevel, String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT s FROM Spanish s WHERE s.languageLevel = :lang AND s.proficiencyLevel = :level", 
                Spanish.class)
                .setParameter("lang", languageLevel)
                .setParameter("level", proficiencyLevel)
                .getResultList();
    }
} 