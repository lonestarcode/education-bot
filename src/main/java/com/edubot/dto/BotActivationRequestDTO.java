package com.edubot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for Bot Activation related requests.
 */
@Getter
@Setter
public class BotActivationRequestDTO {

    @NotBlank(message = "Input text is required for Bot Activation operations.")
    private String inputText;
}