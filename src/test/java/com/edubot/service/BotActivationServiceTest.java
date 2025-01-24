package com.edubot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BotActivationServiceTest {
    
    @MockBean
    private StudentService studentService;
    
    @Autowired
    private BotActivationService botActivationService;
    
    @Test
    void testBotActivation() {
        // Given
        Long studentId = 1L;
        BotActivationRequestDTO request = new BotActivationRequestDTO();
        
        // When
        when(studentService.getStudent(studentId)).thenReturn(new StudentProfile());
        BotActivationResponseDTO response = botActivationService.activate(request);
        
        // Then
        assertNotNull(response);
        verify(studentService).getStudent(studentId);
    }
} 