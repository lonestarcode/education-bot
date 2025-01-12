package com.edubot.model.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Represents metadata extracted from PDF files, such as title, author, subject,
 * and creation/modification dates.
 */
@Entity
@Table(name = "pdf_metadata")
public class PDFMetadata {

    private static final Logger logger = LoggerFactory.getLogger(PDFMetadata.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "PDF title cannot be blank.")
    @Size(max = 255, message = "PDF title must not exceed 255 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author name cannot be blank.")
    @Size(max = 255, message = "Author name must not exceed 255 characters.")
    @Column(nullable = false)
    private String author;

    @Size(max = 500, message = "Subject must not exceed 500 characters.")
    private String subject;

    @NotNull(message = "Page count cannot be null.")
    @Column(nullable = false)
    private Integer pageCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @NotBlank(message = "File path cannot be blank.")
    @Column(nullable = false, unique = true)
    private String filePath;

    @Column
    private LocalDateTime pdfCreatedDate;

    @Column
    private LocalDateTime pdfModifiedDate;

    // ========================== Constructors ==========================

    public PDFMetadata() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("PDFMetadata instance created with default values.");
    }

    public PDFMetadata(String title, String author, String subject, Integer pageCount, String filePath,
                       LocalDateTime pdfCreatedDate, LocalDateTime pdfModifiedDate) {
        this();
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.pageCount = pageCount;
        this.filePath = filePath;
        this.pdfCreatedDate = pdfCreatedDate;
        this.pdfModifiedDate = pdfModifiedDate;
    }

    // ========================== Lifecycle Hooks ==========================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        logger.info("PDFMetadata persisted at {}", createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        logger.info("PDFMetadata updated at {}", updatedAt);
    }

    // ========================== Business Logic ==========================

    /**
     * Updates PDF metadata details.
     *
     * @param title   New title.
     * @param author  New author.
     * @param subject New subject.
     * @param pageCount Updated page count.
     */
    public void updateMetadata(String title, String author, String subject, Integer pageCount) {
        this.title = title != null ? title : this.title;
        this.author = author != null ? author : this.author;
        this.subject = subject != null ? subject : this.subject;
        this.pageCount = pageCount != null ? pageCount : this.pageCount;
        logger.info("PDF metadata updated: Title={}, Author={}, Subject={}, PageCount={}",
                title, author, subject, pageCount);
    }

    /**
     * Validates file path format.
     */
    public void validateFilePath() {
        if (this.filePath == null || !this.filePath.endsWith(".pdf")) {
            throw new IllegalArgumentException("Invalid file path. Must point to a PDF file.");
        }
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
        logger.info("PDF title updated: {}", title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        logger.info("PDF author updated: {}", author);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        logger.info("PDF subject updated: {}", subject);
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        logger.info("PDF page count updated: {}", pageCount);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        validateFilePath();
        this.filePath = filePath;
        logger.info("PDF file path updated: {}", filePath);
    }

    public LocalDateTime getPdfCreatedDate() {
        return pdfCreatedDate;
    }

    public void setPdfCreatedDate(LocalDateTime pdfCreatedDate) {
        this.pdfCreatedDate = pdfCreatedDate;
        logger.info("PDF created date updated: {}", pdfCreatedDate);
    }

    public LocalDateTime getPdfModifiedDate() {
        return pdfModifiedDate;
    }

    public void setPdfModifiedDate(LocalDateTime pdfModifiedDate) {
        this.pdfModifiedDate = pdfModifiedDate;
        logger.info("PDF modified date updated: {}", pdfModifiedDate);
    }

    // ========================== Override toString ==========================

    @Override
    public String toString() {
        return "PDFMetadata{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", pageCount=" + pageCount +
                ", filePath='" + filePath + '\'' +
                ", pdfCreatedDate=" + pdfCreatedDate +
                ", pdfModifiedDate=" + pdfModifiedDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}