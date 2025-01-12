package com.edubot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for lecture API responses.
 */
@Getter
@Setter
public class LectureResponseDTO {
    private Long id;
    private String title;
    private String topic;
    private LocalDateTime scheduledTime;
    private String contentBody;
}