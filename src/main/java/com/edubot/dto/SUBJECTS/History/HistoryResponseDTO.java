package com.edubot.dto.subjects.history;

import lombok.*;

/**
 * DTO for returning History content details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDTO {
    private Long id;
    private String title;
    private String contentBody;
    private String focusArea;
    private String proficiencyLevel;
    private String createdAt;
    private String updatedAt;
}