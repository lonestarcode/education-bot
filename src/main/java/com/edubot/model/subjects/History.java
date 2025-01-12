package com.edubot.model.subjects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Represents content for the History subject.
 */
@Entity
@Table(name = "history_content")
public class History {

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

    @NotBlank(message = "Time period cannot be blank")
    @Column(nullable = false)
    private String timePeriod;

    @NotBlank(message = "Proficiency level cannot be blank")
    @Column(nullable = false)
    private String proficiencyLevel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ======= Constructors =======

    public History() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public History(String title, String contentBody, String timePeriod, String proficiencyLevel) {
        this.title = title;
        this.contentBody = contentBody;
        this.timePeriod = timePeriod;
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

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        if (timePeriod == null || timePeriod.trim().isEmpty()) {
            throw new IllegalArgumentException("Time period cannot be blank");
        }
        this.timePeriod = timePeriod;
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
        return "History{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentBody='" + contentBody + '\'' +
                ", timePeriod='" + timePeriod + '\'' +
                ", proficiencyLevel='" + proficiencyLevel + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}