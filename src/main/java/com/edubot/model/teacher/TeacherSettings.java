package com.edubot.model.teacher;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents teacher-specific configurations and preferences for managing
 * classrooms, lectures, and educational content.
 */
@Entity
@Table(name = "teacher_settings")
public class TeacherSettings {

    private static final Logger logger = LoggerFactory.getLogger(TeacherSettings.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Max students per lecture cannot be null.")
    @Min(value = 5, message = "Minimum 5 students per lecture.")
    @Max(value = 100, message = "Maximum 100 students per lecture.")
    @Column(nullable = false)
    private Integer maxStudentsPerLecture = 30;

    @NotNull(message = "Allow homework submissions cannot be null.")
    @Column(nullable = false)
    private Boolean allowHomeworkSubmissions = true;

    @NotNull(message = "Enable class recordings cannot be null.")
    @Column(nullable = false)
    private Boolean enableClassRecordings = false;

    @NotNull(message = "Default language cannot be null.")
    @Size(min = 2, max = 10, message = "Language code must be between 2 and 10 characters.")
    @Column(nullable = false)
    private String defaultLanguage = "en";

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "teacher_dynamic_settings",
        joinColumns = @JoinColumn(name = "teacher_settings_id")
    )
    @MapKeyColumn(name = "setting_key")
    @Column(name = "setting_value")
    @Size(max = 50, message = "Dynamic settings cannot exceed 50 entries.")
    private Map<String, String> dynamicSettings = new HashMap<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ========================== Constructors ==========================

    public TeacherSettings() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("TeacherSettings created with default values.");
    }

    public TeacherSettings(Integer maxStudentsPerLecture, Boolean allowHomeworkSubmissions,
                           Boolean enableClassRecordings, String defaultLanguage) {
        this();
        this.maxStudentsPerLecture = maxStudentsPerLecture;
        this.allowHomeworkSubmissions = allowHomeworkSubmissions;
        this.enableClassRecordings = enableClassRecordings;
        this.defaultLanguage = defaultLanguage;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("TeacherSettings persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("TeacherSettings updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Add a dynamic setting for the teacher.
     *
     * @param key   Setting key.
     * @param value Setting value.
     */
    public void addDynamicSetting(String key, String value) {
        if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Dynamic setting key and value must not be empty.");
        }
        if (dynamicSettings.size() >= 50) {
            throw new IllegalStateException("Maximum dynamic settings limit reached (50).");
        }
        dynamicSettings.put(key, value);
        logger.info("Added dynamic setting: {} -> {}", key, value);
    }

    /**
     * Update default language.
     *
     * @param language New language code.
     */
    public void updateLanguage(String language) {
        if (language == null || language.length() < 2 || language.length() > 10) {
            throw new IllegalArgumentException("Language code must be between 2 and 10 characters.");
        }
        this.defaultLanguage = language;
        logger.info("Default language updated to: {}", language);
    }

    /**
     * Toggle homework submissions.
     *
     * @param allow Boolean value to enable/disable homework submissions.
     */
    public void toggleHomeworkSubmissions(boolean allow) {
        this.allowHomeworkSubmissions = allow;
        logger.info("Homework submissions allowed: {}", allow);
    }

    /**
     * Toggle class recordings.
     *
     * @param enable Boolean value to enable/disable class recordings.
     */
    public void toggleClassRecordings(boolean enable) {
        this.enableClassRecordings = enable;
        logger.info("Class recordings enabled: {}", enable);
    }

    // ========================== Getters and Setters ==========================

    public Long getId() {
        return id;
    }

    public Integer getMaxStudentsPerLecture() {
        return maxStudentsPerLecture;
    }

    public void setMaxStudentsPerLecture(Integer maxStudentsPerLecture) {
        this.maxStudentsPerLecture = maxStudentsPerLecture;
        logger.info("Max students per lecture updated to: {}", maxStudentsPerLecture);
    }

    public Boolean getAllowHomeworkSubmissions() {
        return allowHomeworkSubmissions;
    }

    public void setAllowHomeworkSubmissions(Boolean allowHomeworkSubmissions) {
        this.allowHomeworkSubmissions = allowHomeworkSubmissions;
        logger.info("Allow homework submissions updated: {}", allowHomeworkSubmissions);
    }

    public Boolean getEnableClassRecordings() {
        return enableClassRecordings;
    }

    public void setEnableClassRecordings(Boolean enableClassRecordings) {
        this.enableClassRecordings = enableClassRecordings;
        logger.info("Enable class recordings updated: {}", enableClassRecordings);
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        logger.info("Default language updated: {}", defaultLanguage);
    }

    public Map<String, String> getDynamicSettings() {
        return dynamicSettings;
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
        return "TeacherSettings{" +
                "id=" + id +
                ", maxStudentsPerLecture=" + maxStudentsPerLecture +
                ", allowHomeworkSubmissions=" + allowHomeworkSubmissions +
                ", enableClassRecordings=" + enableClassRecordings +
                ", defaultLanguage='" + defaultLanguage + '\'' +
                ", dynamicSettings=" + dynamicSettings +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}