package com.edubot.dto.subjects.art;

import lombok.*;

/**
 * DTO for returning Art content details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtResponseDTO {
    private Long id;
    private String title;
    private String contentBody;
    private String focusArea;
    private String proficiencyLevel;
    private String createdAt;
    private String updatedAt;
}