package test.java.com.edubot.integration;

import com.edubot.integrations.ScienceIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ScienceIntegrationTest {

    @Autowired
    private ScienceIntegrationService scienceIntegrationService;

    @Test
    void testFetchScientificData() {
        CompletableFuture<List<String>> dataFuture = 
            scienceIntegrationService.fetchScientificData("physics");
        
        List<String> data = dataFuture.join();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    void testVirtualLabIntegration() {
        assertDoesNotThrow(() -> 
            scienceIntegrationService.integrateWithVirtualLab(1L));
    }

    @Test
    void testSciencePlatformSync() {
        assertDoesNotThrow(() -> 
            scienceIntegrationService.syncWithSciencePlatforms());
    }
}
