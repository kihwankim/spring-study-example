package com.example.batch.config;

import com.example.batch.jobparameter.CreateDateJobParameter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class AppConfig {

    @Bean
    @JobScope
    public CreateDateJobParameter jobParameter() {
        return new CreateDateJobParameter();
    }
}
