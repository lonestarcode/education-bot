package com.edubot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseResponseDTO serves as a standard response structure.
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseResponseDTO {
    private String status;
    private String message;
}