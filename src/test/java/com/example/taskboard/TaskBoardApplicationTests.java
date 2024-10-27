package com.example.taskboard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.logging.Logger;

/**
 * Test class for the main application.
 * Ensures that the Spring Boot application context loads successfully.
 */
@SpringBootTest
public class TaskBoardApplicationTests {

    private static final Logger logger = Logger.getLogger(TaskBoardApplicationTests.class.getName());

    /**
     * Test to verify that the application context loads successfully.
     */
    @Test
    void contextLoads() {
        logger.info("Running contextLoads test to verify application context loads successfully");

        // This test will pass if the application context loads without any issues
    }
}