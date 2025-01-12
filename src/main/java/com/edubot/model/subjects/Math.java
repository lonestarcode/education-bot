package com.edubot.model.subjects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Represents content specific to Math subjects.
 */
@Entity
@Table(name = "math_content")
public class Math {

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

    public Math() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Math(String title, String contentBody, String focusArea, String proficiencyLevel) {
        this.title = title;
        this.contentBody = contentBody;
        this.focusArea = focusArea;
        this.proficiencyLevel = proficiencyLevel;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        if (contentBody != null) {
            this.contentBody = sanitizeContent(contentBody);
        } else {
            throw new IllegalArgumentException("Content body cannot be null");
        }
    }

    private String sanitizeContent(String content) {
        return content.replaceAll("<[^>]*>", "").trim(); // Basic sanitization
    }

    @Override
    public String toString() {
        return "MathContent{" +
                "contentBody='" + contentBody + '\'' +
                ", " + super.toString() +
                '}';
    }
}