package com.siscred.cooperativa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "spring.kafka.listener.auto-startup=false")
@SpringBootTest
class CooperativaApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> CooperativaApplication.main(new String[]{}));
    }
}
