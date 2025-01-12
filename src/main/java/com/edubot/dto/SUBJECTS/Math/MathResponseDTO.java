package com.edubot.dto.subjects.math;

import lombok.*;

/**
 * DTO for returning Math content details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MathResponseDTO {
    private Long id;
    private String title;
    private String contentBody;
    private String focusArea;
    private String proficiencyLevel;
    private String createdAt;
    private String updatedAt;
}