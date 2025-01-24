package com.edubot.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String difficulty;
    private Integer durationMinutes;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
