package com.edubot.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for lecture creation and updates.
 */
@Getter
@Setter
public class LectureRequestDTO {

    @NotBlank(message = "Lecture title is required.")
    private String title;

    @NotBlank(message = "Lecture topic is required.")
    private String topic;

    @NotNull(message = "Scheduled time is required.")
    @Future(message = "Scheduled time must be in the future.")
    private LocalDateTime scheduledTime;

    private String contentBody;
}