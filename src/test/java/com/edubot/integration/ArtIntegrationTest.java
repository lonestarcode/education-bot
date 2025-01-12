package test.java.com.edubot.integration;

import com.edubot.integrations.ArtIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ArtIntegrationTest {

    @Autowired
    private ArtIntegrationService artIntegrationService;

    @Test
    void testFetchExternalArtResources() {
        CompletableFuture<List<String>> resourcesFuture = 
            artIntegrationService.fetchExternalArtResources("painting");
        
        List<String> resources = resourcesFuture.join();
        assertNotNull(resources);
        assertFalse(resources.isEmpty());
    }

    @Test
    void testVirtualGalleryIntegration() {
        assertDoesNotThrow(() -> 
            artIntegrationService.integrateWithVirtualGallery(1L));
    }

    @Test
    void testArtPlatformSync() {
        assertDoesNotThrow(() -> 
            artIntegrationService.syncWithArtPlatforms());
    }
}
