package com.edubot.model.subjects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Represents content for the English subject.
 */
@Entity
@Table(name = "english_content")
public class English {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content body cannot be blank")
    @Size(min = 10, message = "Content body must be at least 10 characters long")
    @Column(nullable = false, length = 1000)
    private String contentBody;

    @NotBlank(message = "Focus area cannot be blank")
    @Column(nullable = false)
    private String focusArea;

    @NotBlank(message = "Proficiency level cannot be blank")
    @Column(nullable = false)
    private String proficiencyLevel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ======= Constructors =======

    public English() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public English(String title, String contentBody, String focusArea, String proficiencyLevel) {
        this.title = title;
        this.contentBody = contentBody;
        this.focusArea = focusArea;
        this.proficiencyLevel = proficiencyLevel;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ======= Getters and Setters =======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        if (contentBody == null || contentBody.length() < 10) {
            throw new IllegalArgumentException("Content body must be at least 10 characters long");
        }
        this.contentBody = contentBody;
    }

    public String getFocusArea() {
        return focusArea;
    }

    public void setFocusArea(String focusArea) {
        if (focusArea == null || focusArea.trim().isEmpty()) {
            throw new IllegalArgumentException("Focus area cannot be blank");
        }
        this.focusArea = focusArea;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        if (proficiencyLevel == null || proficiencyLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("Proficiency level cannot be blank");
        }
        this.proficiencyLevel = proficiencyLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ======= Lifecycle Hooks =======

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ======= toString =======

    @Override
    public String toString() {
        return "English{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentBody='" + contentBody + '\'' +
                ", focusArea='" + focusArea + '\'' +
                ", proficiencyLevel='" + proficiencyLevel + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}