package com.example.redisexample.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointRedisServiceTest {

    @Autowired
    PointRedisService pointRedisService;

    @Test
    void saveHashMapValueTest() {
        pointRedisService.saveHashMapValue();
    }

    @Test
    void findAllFromHashTest() throws Exception {
        pointRedisService.getAllMapValue();
    }

    @Test
    void findMultiGetTest() throws Exception {
        pointRedisService.getMultiGet();
    }

    @Test
    void findHashValueTest() throws Exception {
        pointRedisService.getForOnlyValue();
    }

    @Test
    void hscanValuesTest() throws Exception {
        pointRedisService.hscanDataFromHash();
    }

    @Test
    void rightPushTest() throws Exception {
        pointRedisService.saveListToRight("val1");
    }

    @Test
    void findAllList() throws Exception {
        pointRedisService.findAllFromList();
    }
}