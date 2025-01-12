package com.edubot.model.student;

import com.edubot.model.personality.PersonalityProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a student's profile containing personal information,
 * personality profile, and academic-related metadata.
 */
@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    private static final Logger logger = LoggerFactory.getLogger(StudentProfile.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student name cannot be blank.")
    @Size(min = 2, max = 50, message = "Student name must be between 2 and 50 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Date of birth cannot be null.")
    @Past(message = "Date of birth must be a past date.")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Grade cannot be blank.")
    @Column(nullable = false)
    private String grade; // e.g., Grade 10, Grade 11

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personality_profile_id", referencedColumnName = "id")
    private PersonalityProfile personalityProfile;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ========================== Constructors ==========================

    public StudentProfile() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("StudentProfile created with default timestamps.");
    }

    public StudentProfile(String name, String email, LocalDate dateOfBirth, String grade, PersonalityProfile personalityProfile) {
        this();
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.grade = grade;
        this.personalityProfile = personalityProfile;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("StudentProfile persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("StudentProfile updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Update the student's grade.
     */
    public void updateGrade(String newGrade) {
        if (newGrade == null || newGrade.trim().isEmpty()) {
            throw new IllegalArgumentException("Grade cannot be null or empty.");
        }
        this.grade = newGrade;
        this.updatedAt = LocalDateTime.now();
        logger.info("Grade updated for Student: {}", name);
    }

    /**
     * Update the student's email.
     */
    public void updateEmail(String newEmail) {
        if (newEmail == null || !newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
        logger.info("Email updated for Student: {}", name);
    }

    /**
     * Update the personality profile.
     */
    public void updatePersonalityProfile(PersonalityProfile newProfile) {
        if (newProfile == null) {
            throw new IllegalArgumentException("Personality profile cannot be null.");
        }
        this.personalityProfile = newProfile;
        this.updatedAt = LocalDateTime.now();
        logger.info("Personality profile updated for Student: {}", name);
    }

    // ========================== Getters and Setters ==========================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        logger.info("Name updated: {}", name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        logger.info("Email updated: {}", email);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        logger.info("Date of birth updated: {}", dateOfBirth);
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        logger.info("Grade updated: {}", grade);
    }

    public PersonalityProfile getPersonalityProfile() {
        return personalityProfile;
    }

    public void setPersonalityProfile(PersonalityProfile personalityProfile) {
        this.personalityProfile = personalityProfile;
        logger.info("Personality profile updated.");
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
        return "StudentProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", grade='" + grade + '\'' +
                ", personalityProfile=" + (personalityProfile != null ? personalityProfile.getId() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}