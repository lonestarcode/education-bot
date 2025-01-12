package com.edubot.model.personality;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Represents a student's personality profile for adaptive learning.
 * Stores preferences, strengths, weaknesses, and behavioral patterns.
 */
@Entity
@Table(name = "personality_profiles")
public class PersonalityProfile {

    private static final Logger logger = LoggerFactory.getLogger(PersonalityProfile.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student ID cannot be blank.")
    @Column(nullable = false, unique = true)
    private String studentId;

    @NotBlank(message = "Learning style cannot be blank.")
    @Column(nullable = false)
    private String learningStyle; // e.g., Visual, Auditory, Kinesthetic

    @NotBlank(message = "Strengths cannot be blank.")
    @Column(nullable = false)
    private String strengths;

    @NotBlank(message = "Weaknesses cannot be blank.")
    @Column(nullable = false)
    private String weaknesses;

    @Column
    private String preferredSubjects;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ========================== Constructors ==========================

    public PersonalityProfile() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("PersonalityProfile created with default timestamps.");
    }

    public PersonalityProfile(String studentId, String learningStyle, String strengths, String weaknesses, String preferredSubjects) {
        this();
        this.studentId = studentId;
        this.learningStyle = learningStyle;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.preferredSubjects = preferredSubjects;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("PersonalityProfile persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("PersonalityProfile updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Update strengths for the personality profile.
     */
    public void updateStrengths(String newStrengths) {
        if (newStrengths == null || newStrengths.trim().isEmpty()) {
            throw new IllegalArgumentException("Strengths cannot be blank or null.");
        }
        this.strengths = newStrengths;
        this.updatedAt = LocalDateTime.now();
        logger.info("PersonalityProfile strengths updated for Student ID: {}", studentId);
    }

    /**
     * Update weaknesses for the personality profile.
     */
    public void updateWeaknesses(String newWeaknesses) {
        if (newWeaknesses == null || newWeaknesses.trim().isEmpty()) {
            throw new IllegalArgumentException("Weaknesses cannot be blank or null.");
        }
        this.weaknesses = newWeaknesses;
        this.updatedAt = LocalDateTime.now();
        logger.info("PersonalityProfile weaknesses updated for Student ID: {}", studentId);
    }

    /**
     * Update preferred subjects.
     */
    public void updatePreferredSubjects(String newSubjects) {
        this.preferredSubjects = newSubjects;
        this.updatedAt = LocalDateTime.now();
        logger.info("Preferred subjects updated for Student ID: {}", studentId);
    }

    // ========================== Getters and Setters ==========================

    public Long getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        logger.info("Student ID updated: {}", studentId);
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle;
        logger.info("Learning style updated: {}", learningStyle);
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
        logger.info("Strengths updated: {}", strengths);
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
        logger.info("Weaknesses updated: {}", weaknesses);
    }

    public String getPreferredSubjects() {
        return preferredSubjects;
    }

    public void setPreferredSubjects(String preferredSubjects) {
        this.preferredSubjects = preferredSubjects;
        logger.info("Preferred subjects updated: {}", preferredSubjects);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ========================== Override toString ==========================

    @Override
    public String toString() {
        return "PersonalityProfile{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", learningStyle='" + learningStyle + '\'' +
                ", strengths='" + strengths + '\'' +
                ", weaknesses='" + weaknesses + '\'' +
                ", preferredSubjects='" + preferredSubjects + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}