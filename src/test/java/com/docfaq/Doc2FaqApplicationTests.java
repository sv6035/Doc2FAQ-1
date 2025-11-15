package com.docfaq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Integration tests for the Doc2FAQ application.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class Doc2FaqApplicationTests {

    /**
     * Test that the Spring application context loads successfully.
     */
    @Test
    void contextLoads() {
        // This test will pass if the application context loads without errors
    }
}