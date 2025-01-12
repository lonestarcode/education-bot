package test.java.com.edubot.integration;

import com.edubot.integrations.MathIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MathIntegrationTest {

    @Autowired
    private MathIntegrationService mathIntegrationService;

    @Test
    void testFetchMathProblems() {
        CompletableFuture<List<String>> problemsFuture = 
            mathIntegrationService.fetchMathProblems("algebra");
        
        List<String> problems = problemsFuture.join();
        assertNotNull(problems);
        assertFalse(problems.isEmpty());
    }

    @Test
    void testVisualizationToolIntegration() {
        assertDoesNotThrow(() -> 
            mathIntegrationService.integrateWithVisualizationTool(1L));
    }

    @Test
    void testMathPlatformSync() {
        assertDoesNotThrow(() -> 
            mathIntegrationService.syncWithMathPlatforms());
    }
}
