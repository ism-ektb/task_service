package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for simple App.
 */
@SpringBootTest
class TaskTests {

    @org.junit.jupiter.api.Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(TaskServiceApplication::new);
        Assertions.assertDoesNotThrow(() -> TaskServiceApplication.main(new String[]{}));
    }

}
