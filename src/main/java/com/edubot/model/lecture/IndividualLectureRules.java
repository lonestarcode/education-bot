package com.edubot.model.lecture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents individual rules configured for specific lectures, overriding default rules where applicable.
 */
@Entity
@Table(name = "individual_lecture_rules")
public class IndividualLectureRules {

    private static final Logger logger = LoggerFactory.getLogger(IndividualLectureRules.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Lecture ID cannot be null.")
    @Column(nullable = false)
    private Long lectureId;

    @Column(nullable = false)
    private boolean enableQuizzes = true;

    @Column(nullable = false)
    private boolean enableHomework = true;

    @Column(nullable = false)
    private boolean enableInteractions = true;

    @Column(nullable = false)
    private boolean recordLectures = false;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "individual_dynamic_rules",
        joinColumns = @JoinColumn(name = "individual_rules_id")
    )
    @MapKeyColumn(name = "rule_key")
    @Column(name = "rule_value")
    @Size(max = 100, message = "Dynamic rules cannot exceed 100 entries.")
    private Map<String, String> dynamicRules = new HashMap<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ========================== Constructors ==========================

    public IndividualLectureRules() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("IndividualLectureRules created with default values.");
    }

    public IndividualLectureRules(Long lectureId) {
        this();
        this.lectureId = lectureId;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("IndividualLectureRules persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("IndividualLectureRules updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Enable all rules for the lecture.
     */
    public void enableAllRules() {
        this.enableQuizzes = true;
        this.enableHomework = true;
        this.enableInteractions = true;
        this.recordLectures = true;
        logger.info("All individual lecture rules enabled for lecture ID: {}", lectureId);
    }

    /**
     * Disable all rules for the lecture.
     */
    public void disableAllRules() {
        this.enableQuizzes = false;
        this.enableHomework = false;
        this.enableInteractions = false;
        this.recordLectures = false;
        logger.info("All individual lecture rules disabled for lecture ID: {}", lectureId);
    }

    /**
     * Add or update a dynamic rule.
     */
    public void addDynamicRule(String key, String value) {
        if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Dynamic rule key and value must not be empty.");
        }
        if (dynamicRules.size() >= 100) {
            throw new IllegalStateException("Maximum dynamic rules limit reached (100).");
        }
        dynamicRules.put(key, value);
        logger.info("Added/Updated dynamic rule: {} -> {} for lecture ID: {}", key, value, lectureId);
    }

    /**
     * Remove a dynamic rule.
     */
    public void removeDynamicRule(String key) {
        if (dynamicRules.containsKey(key)) {
            dynamicRules.remove(key);
            logger.info("Removed dynamic rule: {} for lecture ID: {}", key, lectureId);
        } else {
            logger.warn("Attempted to remove non-existent dynamic rule: {} for lecture ID: {}", key, lectureId);
        }
    }

    // ========================== Getters and Setters ==========================

    public Long getId() {
        return id;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public boolean isEnableQuizzes() {
        return enableQuizzes;
    }

    public void setEnableQuizzes(boolean enableQuizzes) {
        this.enableQuizzes = enableQuizzes;
    }

    public boolean isEnableHomework() {
        return enableHomework;
    }

    public void setEnableHomework(boolean enableHomework) {
        this.enableHomework = enableHomework;
    }

    public boolean isEnableInteractions() {
        return enableInteractions;
    }

    public void setEnableInteractions(boolean enableInteractions) {
        this.enableInteractions = enableInteractions;
    }

    public boolean isRecordLectures() {
        return recordLectures;
    }

    public void setRecordLectures(boolean recordLectures) {
        this.recordLectures = recordLectures;
    }

    public Map<String, String> getDynamicRules() {
        return Map.copyOf(dynamicRules); // Return an immutable map
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
        return "IndividualLectureRules{" +
                "id=" + id +
                ", lectureId=" + lectureId +
                ", enableQuizzes=" + enableQuizzes +
                ", enableHomework=" + enableHomework +
                ", enableInteractions=" + enableInteractions +
                ", recordLectures=" + recordLectures +
                ", dynamicRules=" + dynamicRules +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}