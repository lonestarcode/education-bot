package com.edubot.dto.SUBJECTS.Spanish;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO for responding with Spanish content details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpanishResponseDTO {
    private Long id;
    private String title;
    private String contentBody;
    private String focusArea;
    private String proficiencyLevel;
    private String createdAt;
    private String updatedAt;
}