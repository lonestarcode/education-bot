package com.edubot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for study material API requests.
 */
@Getter
@Setter
public class StudyMaterialRequestDTO {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Content is required.")
    private String content;

    @NotBlank(message = "Subject is required.")
    private String subject;

    private String filePath;
}