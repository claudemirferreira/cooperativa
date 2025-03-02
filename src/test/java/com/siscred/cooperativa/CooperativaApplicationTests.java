package com.siscred.cooperativa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CooperativaApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> CooperativaApplication.main(new String[]{}));
    }
}
