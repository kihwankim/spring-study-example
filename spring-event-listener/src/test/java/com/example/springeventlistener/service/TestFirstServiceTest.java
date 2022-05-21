package com.example.springeventlistener.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestFirstServiceTest {

    @Autowired
    TestFirstService service;

    @Test
    void runTest() {
        service.run(123L);
    }

    @Test
    void runNewTest() throws Exception {
        service.runNew(234L);
    }
}