package com.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class TestBatchLegacyConfig {
    @Bean
    public JobRepositoryTestUtils jobRepositoryTestUtils() {
        return new JobRepositoryTestUtils();
    }
}
