package com.edubot.dto.SUBJECTS.Spanish;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating or updating Spanish content.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpanishRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content body cannot be blank")
    @Size(min = 10, message = "Content body must be at least 10 characters long")
    private String contentBody;

    @NotBlank(message = "Focus area cannot be blank")
    private String focusArea;

    @NotBlank(message = "Proficiency level cannot be blank")
    private String proficiencyLevel;
}