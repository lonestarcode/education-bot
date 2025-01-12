package com.edubot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for standard API response messages.
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseMessageDTO {
    private String message;
}