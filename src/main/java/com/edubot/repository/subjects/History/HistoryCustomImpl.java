package com.edubot.repository.subjects.History;

import com.edubot.model.subjects.History;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for History content.
 */
@Repository
public class HistoryCustomImpl implements HistoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<History> findRecentHistoryContent(int limit) {
        return entityManager.createQuery(
                "SELECT h FROM History h ORDER BY h.createdAt DESC", History.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countHistoryContentByProficiencyLevel(String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(h) FROM History h WHERE h.proficiencyLevel = :level", Long.class)
                .setParameter("level", proficiencyLevel)
                .getSingleResult();
    }

    @Override
    public List<History> findByTimePeriodAndLevel(String timePeriod, String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT h FROM History h WHERE h.timePeriod = :period AND h.proficiencyLevel = :level", 
                History.class)
                .setParameter("period", timePeriod)
                .setParameter("level", proficiencyLevel)
                .getResultList();
    }
} 