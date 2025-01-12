package com.edubot.model.lecture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents default rules and configurations for all lectures in a classroom.
 * Applies default settings unless overridden by IndividualLectureRules.
 */
@Entity
@Table(name = "default_lecture_rules")
public class DefaultLectureRules {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLectureRules.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        name = "default_dynamic_rules",
        joinColumns = @JoinColumn(name = "default_rules_id")
    )
    @MapKeyColumn(name = "rule_key")
    @Column(name = "rule_value", nullable = false)
    @Size(max = 100, message = "Dynamic rules cannot exceed 100 entries.")
    private Map<@NotBlank String, @NotBlank String> dynamicRules = new HashMap<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Default constructor initializes timestamps.
     */
    public DefaultLectureRules() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("DefaultLectureRules initialized with default values.");
    }

    // ========================= LIFECYCLE CALLBACKS =========================
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("DefaultLectureRules persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("DefaultLectureRules updated at {}", updatedAt);
    }

    // ========================= RULE MANAGEMENT =========================
    
    /**
     * Enable all default lecture rules.
     */
    public void enableAllRules() {
        this.enableQuizzes = true;
        this.enableHomework = true;
        this.enableInteractions = true;
        this.recordLectures = true;
        logger.info("All default lecture rules enabled.");
    }

    /**
     * Disable all default lecture rules.
     */
    public void disableAllRules() {
        this.enableQuizzes = false;
        this.enableHomework = false;
        this.enableInteractions = false;
        this.recordLectures = false;
        logger.info("All default lecture rules disabled.");
    }

    /**
     * Add a dynamic rule.
     *
     * @param key   Rule key.
     * @param value Rule value.
     */
    public void addDynamicRule(@NotBlank String key, @NotBlank String value) {
        if (dynamicRules.size() >= 100) {
            throw new IllegalStateException("Maximum dynamic rules limit reached (100).");
        }
        dynamicRules.put(key, value);
        logger.info("Added dynamic rule: {} -> {}", key, value);
    }

    /**
     * Remove a dynamic rule by key.
     *
     * @param key Rule key to remove.
     */
    public void removeDynamicRule(@NotBlank String key) {
        if (dynamicRules.remove(key) != null) {
            logger.info("Removed dynamic rule: {}", key);
        } else {
            logger.warn("Attempted to remove non-existing dynamic rule: {}", key);
        }
    }

    /**
     * Retrieve an immutable view of dynamic rules.
     *
     * @return Immutable Map of dynamic rules.
     */
    public Map<String, String> getDynamicRules() {
        return Collections.unmodifiableMap(dynamicRules);
    }

    // ========================= GETTERS AND SETTERS =========================
    
    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "DefaultLectureRules{id=" + id + 
               ", enableQuizzes=" + enableQuizzes +
               ", enableHomework=" + enableHomework +
               ", enableInteractions=" + enableInteractions +
               ", recordLectures=" + recordLectures + "}";
    }
}