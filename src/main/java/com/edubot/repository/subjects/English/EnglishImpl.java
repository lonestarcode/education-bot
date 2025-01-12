package com.edubot.repository.subjects.English;

import com.edubot.model.subject.EnglishContent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for English subject content.
 */
@Repository
public class EnglishImpl implements EnglishCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<English> findRecentEnglishTopics(int limit) {
        return entityManager.createQuery(
                "SELECT e FROM EnglishContent e ORDER BY e.createdAt DESC", English.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countEnglishTopicsByDifficulty(String difficultyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(e) FROM EnglishContent e WHERE e.difficulty = :difficultyLevel", Long.class)
                .setParameter("difficultyLevel", difficultyLevel)
                .getSingleResult();
    }
}