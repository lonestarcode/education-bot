package com.edubot.dto.subjects.science;

import lombok.*;

/**
 * DTO for returning Science content details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScienceResponseDTO {
    private Long id;
    private String title;
    private String contentBody;
    private String focusArea;
    private String proficiencyLevel;
    private String createdAt;
    private String updatedAt;
}