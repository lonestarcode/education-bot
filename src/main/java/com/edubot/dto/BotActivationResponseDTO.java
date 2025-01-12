package com.edubot.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for Bot Activation related responses.
 */
@Getter
@Setter
public class BotActivationResponseDTO {
    private String result;

    public BotActivationResponseDTO(String result) {
        this.result = result;
    }
}