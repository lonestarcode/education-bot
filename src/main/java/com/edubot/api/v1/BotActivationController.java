package com.edubot.controller.ai;

import com.edubot.dto.BotActivationRequestDTO;
import com.edubot.dto.BotActivationResponseDTO;
import com.edubot.service.BotActivationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.edubot.config.ApiRoutes.*;

/**
 * BotActivationController manages AI and PDF orchestration tasks.
 */
@RestController
@RequestMapping(AI)
public class BotActivationController {

    private static final Logger logger = LoggerFactory.getLogger(BotActivationController.class);
    private final BotActivationService botActivationService;

    @Autowired
    public BotActivationController(BotActivationService botActivationService) {
        this.botActivationService = botActivationService;
    }

    // ✅ Summarize Text
    @PostMapping(AI_SUMMARIZE)
    public ResponseEntity<BotActivationResponseDTO> summarizeText(@Valid @RequestBody BotActivationRequestDTO request) {
        logger.info("Summarizing text.");
        String result = botActivationService.summarizeText(request.getInputText());
        return ResponseEntity.ok(new BotActivationResponseDTO(result));
    }

    // ✅ Generate Content
    @PostMapping(AI_GENERATE_CONTENT)
    public ResponseEntity<BotActivationResponseDTO> generateContent(@Valid @RequestBody BotActivationRequestDTO request) {
        logger.info("Generating content.");
        String result = botActivationService.generateContent(request.getInputText());
        return ResponseEntity.ok(new BotActivationResponseDTO(result));
    }

    // ✅ Generate Quiz
    @GetMapping(AI_GENERATE_QUIZ)
    public ResponseEntity<BotActivationResponseDTO> generateQuiz(@RequestParam String subject, @RequestParam String topic) {
        logger.info("Generating quiz for subject: {} and topic: {}", subject, topic);
        String result = botActivationService.generateQuiz(String.format("%s - %s", subject, topic));
        return ResponseEntity.ok(new BotActivationResponseDTO(result));
    }

    // ✅ Extract PDF Text
    @PostMapping(AI_PDF_EXTRACT)
    public ResponseEntity<BotActivationResponseDTO> extractPdfText(@RequestParam String filePath) {
        logger.info("Extracting text from PDF: {}", filePath);
        String result = botActivationService.extractTextFromPDF(filePath);
        return ResponseEntity.ok(new BotActivationResponseDTO(result));
    }

    // ✅ Get PDF Metadata
    @GetMapping(AI_PDF_METADATA)
    public ResponseEntity<BotActivationResponseDTO> getPdfMetadata(@RequestParam String filePath) {
        logger.info("Fetching PDF metadata: {}", filePath);
        String result = botActivationService.getPDFMetadata(filePath);
        return ResponseEntity.ok(new BotActivationResponseDTO(result));
    }
}