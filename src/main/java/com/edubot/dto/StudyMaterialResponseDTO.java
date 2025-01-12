package com.edubot.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for study material API responses.
 */
@Getter
@Setter
public class StudyMaterialResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String subject;
    private String filePath;
}