package com.edubot.dto.subjects.english;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating or updating English content.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnglishRequestDTO {

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotBlank(message = "Content body cannot be blank.")
    @Size(min = 10, message = "Content body must be at least 10 characters long.")
    private String contentBody;

    @NotBlank(message = "Focus area cannot be blank.")
    @Size(max = 50, message = "Focus area must not exceed 50 characters.")
    private String focusArea;

    @NotBlank(message = "Proficiency level cannot be blank.")
    @Size(max = 30, message = "Proficiency level must not exceed 30 characters.")
    private String proficiencyLevel;
}