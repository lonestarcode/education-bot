package com.edubot.model.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Represents study material, including content, metadata, and file references.
 */
@Entity
@Table(name = "study_material")
public class StudyMaterial {

    private static final Logger logger = LoggerFactory.getLogger(StudyMaterial.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Study material title cannot be blank.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Study material content cannot be blank.")
    @Size(min = 10, message = "Content must have at least 10 characters.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @NotBlank(message = "Subject cannot be blank.")
    @Column(nullable = false)
    private String subject;

    @Column
    private String filePath;

    @NotNull(message = "CreatedAt timestamp cannot be null.")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "UpdatedAt timestamp cannot be null.")
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ========================== Constructors ==========================

    public StudyMaterial() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("StudyMaterial created with default timestamps.");
    }

    public StudyMaterial(String title, String content, String subject, String filePath) {
        this();
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.filePath = filePath;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("StudyMaterial persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("StudyMaterial updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Update the content of the study material.
     */
    public void updateContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be blank or null.");
        }
        this.content = newContent;
        this.updatedAt = LocalDateTime.now();
        logger.info("StudyMaterial content updated for title: {}", title);
    }

    /**
     * Attach a file reference to the study material.
     */
    public void attachFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be blank or null.");
        }
        this.filePath = filePath;
        this.updatedAt = LocalDateTime.now();
        logger.info("File attached to StudyMaterial: {}", title);
    }

    /**
     * Validate if the study material has an attached file.
     */
    public boolean hasAttachedFile() {
        return this.filePath != null && !this.filePath.trim().isEmpty();
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
        logger.info("StudyMaterial title updated to: {}", title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        logger.info("StudyMaterial content updated.");
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        logger.info("StudyMaterial subject updated to: {}", subject);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        logger.info("StudyMaterial filePath updated to: {}", filePath);
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
        return "StudyMaterial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                ", filePath='" + filePath + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}