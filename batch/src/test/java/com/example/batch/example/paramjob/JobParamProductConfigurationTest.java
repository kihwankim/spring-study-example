package com.example.batch.example.paramjob;

import com.example.batch.TestBatchConfig;
import com.example.batch.entity.product.Product;
import com.example.batch.entity.product.ProductRepository;
import com.example.batch.entity.product.ProductStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {JobProductConfiguration.class, TestBatchConfig.class})
class JobParamProductConfigurationTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("test")
    void jobParamTestConfigTest() throws Exception {
        // given
        LocalDate createDate = LocalDate.of(2020, 9, 26);
        long price = 2000L;
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

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}