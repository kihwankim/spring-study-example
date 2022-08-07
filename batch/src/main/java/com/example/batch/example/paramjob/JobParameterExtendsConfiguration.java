package com.example.batch.example.paramjob;

import com.example.batch.entity.product.Product;
import com.example.batch.jobparameter.CreateDateJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobParameterExtendsConfiguration {
    public static final String JOB_NAME = "jobParameterExtendsBatch";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CreateDateJobParameter jobParameter;

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .preventRestart()
                .build();
    }

    @Bean(name = JOB_NAME + "_step")
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<Product, Product>chunk(chunkSize)
                .reader(reader())
                .writer(writer())
                .build();
    }


    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public JpaPagingItemReader<Product> reader() {

        Map<String, Object> params = new HashMap<>();
        params.put("createDate", jobParameter.getCreateDate());
        params.put("status", jobParameter.getStatus());
        log.info(">>>>>>>>>>> createDate={}, status={}", jobParameter.getCreateDate(), jobParameter.getStatus());

        return new JpaPagingItemReaderBuilder<Product>()
                .name(JOB_NAME + "_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Product p WHERE p.createDate =:createDate AND p.status =:status")
                .parameterValues(params)
                .build();
    }

    private ItemWriter<Product> writer() {
        return items -> {
            for (Product product : items) {
                log.info("Current Product id={}", product.getId());
            }
        };
    }
}
