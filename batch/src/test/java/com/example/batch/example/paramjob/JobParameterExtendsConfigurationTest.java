package com.example.batch.example.paramjob;

import com.example.batch.TestBatchConfig;
import com.example.batch.entity.product.Product;
import com.example.batch.entity.product.ProductRepository;
import com.example.batch.entity.product.ProductStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest(classes = {JobParameterExtendsConfiguration.class, TestBatchConfig.class})
public class JobParameterExtendsConfigurationTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void jobParameterNoarmalTest() throws Exception {
        //given
        LocalDate createDate = LocalDate.of(2019, 9, 26);
        long price = 1000L;
        ProductStatus status = ProductStatus.APPROVE;
        productRepository.save(Product.builder()
                .price(price)
                .createDate(createDate)
                .status(status)
                .build());

        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("createDate", createDate.toString())
                .addString("status", status.name())
                .toJobParameters();
        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
