package com.edubot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for student profile requests.
 */
@Getter
@Setter
public class StudentRequestDTO {

    @NotBlank(message = "Student name is required.")
    private String name;

    @NotNull(message = "Age is required.")
    private Integer age;

    @NotBlank(message = "Grade is required.")
    private String grade;

    private String additionalNotes;
}