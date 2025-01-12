package com.edubot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages chat interactions and message history with learning insights
 */
@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final BotActivationService botActivationService;
    private final ChatHistoryRepository chatHistoryRepository;
    private final LearningAnalyticsService learningAnalyticsService;

    @Autowired
    public ChatService(
            BotActivationService botActivationService, 
            ChatHistoryRepository chatHistoryRepository,
            LearningAnalyticsService learningAnalyticsService) {
        this.botActivationService = botActivationService;
        this.chatHistoryRepository = chatHistoryRepository;
        this.learningAnalyticsService = learningAnalyticsService;
    }

    /**
     * Provides detailed explanations for conceptual questions
     */
    private String generateDetailedExplanation(String question, LearningStyle style) {
        return botActivationService.generateExplanation(question, style);
    }

    /**
     * Retrieve chat history with learning insights
     */
    public ChatHistoryResponse getChatHistory(String studentId) {
        List<ChatMessage> history = chatHistoryRepository.getFullHistory(studentId);
        StudentProgress progress = learningAnalyticsService.getProgress(studentId);
        
        return new ChatHistoryResponse(history, progress);
    }

    private void saveChatInteraction(String studentId, String message, String response) {
        ChatMessage chatMessage = new ChatMessage(
            studentId,
            message,
            response,
            LocalDateTime.now()
        );
        chatHistoryRepository.save(chatMessage);
    }
} 