package com.example.springeventlistener.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestFirstServiceTest {

    @Autowired
    TestPublisherService service;

    @Test
    void runTest() {
        service.run(123L);
    }

    @Test
    void runNewTest() throws Exception {
        service.runNew(234L);
    }

    @Test
    void runInterfaceFirstTest() throws Exception {
        service.runInterfaceFirst(1L, 2L);
    }

    @Test
    void runInterfaceSecTest() throws Exception {
        service.runInterfaceSec(3L, "name");
    }
}