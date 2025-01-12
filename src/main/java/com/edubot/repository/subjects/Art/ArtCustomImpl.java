package com.edubot.repository.subjects.Art;

import com.edubot.model.subjects.Art;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of custom repository for Art content.
 */
@Repository
public class ArtCustomImpl implements ArtCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Art> findRecentArtContent(int limit) {
        return entityManager.createQuery(
                "SELECT a FROM Art a ORDER BY a.createdAt DESC", Art.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countArtContentByProficiencyLevel(String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT COUNT(a) FROM Art a WHERE a.proficiencyLevel = :level", Long.class)
                .setParameter("level", proficiencyLevel)
                .getSingleResult();
    }

    @Override
    public List<Art> findByFocusAreaAndLevel(String focusArea, String proficiencyLevel) {
        return entityManager.createQuery(
                "SELECT a FROM Art a WHERE a.focusArea = :area AND a.proficiencyLevel = :level", 
                Art.class)
                .setParameter("area", focusArea)
                .setParameter("level", proficiencyLevel)
                .getResultList();
    }
} 