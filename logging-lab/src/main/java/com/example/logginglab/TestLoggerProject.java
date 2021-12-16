package com.example.logginglab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestLoggerProject {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestLoggerProject.class);
        for (int count = 0; count < 10; count++) {
            logger.info("info data {}", count);
        }
    }
}
