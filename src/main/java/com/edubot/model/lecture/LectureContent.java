package com.edubot.model.lecture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Represents the content, rules, and metadata of a lecture.
 */
@Entity
@Table(name = "lecture_content")
public class LectureContent {

    private static final Logger logger = LoggerFactory.getLogger(LectureContent.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Lecture title cannot be blank.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Lecture topic cannot be blank.")
    @Column(nullable = false)
    private String topic;

    @NotBlank(message = "Content body cannot be blank.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentBody;

    @NotNull(message = "Scheduled time cannot be null.")
    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    @NotNull(message = "Creation timestamp cannot be null.")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Update timestamp cannot be null.")
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_rules_id")
    private DefaultLectureRules defaultLectureRules;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "individual_rules_id")
    private IndividualLectureRules individualLectureRules;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_material_id")
    private StudyMaterial studyMaterial;

    // ========================== Constructors ==========================

    public LectureContent() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("LectureContent created with default timestamps.");
    }

    public LectureContent(String title, String topic, String contentBody, LocalDateTime scheduledTime) {
        this();
        this.title = title;
        this.topic = topic;
        this.contentBody = contentBody;
        this.scheduledTime = scheduledTime;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("LectureContent persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("LectureContent updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Apply default lecture rules if individual rules are not set.
     */
    public void applyDefaultRules() {
        if (individualLectureRules == null && defaultLectureRules != null) {
            logger.info("Applying default lecture rules to lecture: {}", title);
            this.contentBody = "Default rules applied: " + defaultLectureRules.toString();
        }
    }

    /**
     * Update the content body of the lecture.
     */
    public void updateContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Content body cannot be null or blank.");
        }
        this.contentBody = newContent;
        this.updatedAt = LocalDateTime.now();
        logger.info("Lecture content updated for lecture: {}", title);
    }

    /**
     * Attach study material to the lecture.
     */
    public void attachStudyMaterial(StudyMaterial studyMaterial) {
        if (studyMaterial == null) {
            throw new IllegalArgumentException("Study material cannot be null.");
        }
        this.studyMaterial = studyMaterial;
        logger.info("Study material attached to lecture: {}", title);
    }

    // ========================== Getters and Setters ==========================

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        logger.info("Lecture title updated to: {}", title);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
        logger.info("Lecture topic updated to: {}", topic);
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
        logger.info("Lecture content body updated.");
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
        logger.info("Lecture scheduled time updated to: {}", scheduledTime);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public DefaultLectureRules getDefaultLectureRules() {
        return defaultLectureRules;
    }

    public void setDefaultLectureRules(DefaultLectureRules defaultLectureRules) {
        this.defaultLectureRules = defaultLectureRules;
        logger.info("Default rules applied to lecture: {}", title);
    }

    public IndividualLectureRules getIndividualLectureRules() {
        return individualLectureRules;
    }

    public void setIndividualLectureRules(IndividualLectureRules individualLectureRules) {
        this.individualLectureRules = individualLectureRules;
        logger.info("Individual rules applied to lecture: {}", title);
    }

    public StudyMaterial getStudyMaterial() {
        return studyMaterial;
    }

    public void setStudyMaterial(StudyMaterial studyMaterial) {
        this.studyMaterial = studyMaterial;
        logger.info("Study material updated for lecture: {}", title);
    }

    // ========================== Override toString ==========================

    @Override
    public String toString() {
        return "LectureContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", contentBody='" + contentBody + '\'' +
                ", scheduledTime=" + scheduledTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", defaultLectureRules=" + (defaultLectureRules != null ? defaultLectureRules.getId() : "null") +
                ", individualLectureRules=" + (individualLectureRules != null ? individualLectureRules.getId() : "null") +
                ", studyMaterial=" + (studyMaterial != null ? studyMaterial.getId() : "null") +
                '}';
    }
}