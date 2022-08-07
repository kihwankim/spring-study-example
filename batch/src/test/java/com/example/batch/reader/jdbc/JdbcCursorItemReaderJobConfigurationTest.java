package com.example.batch.reader.jdbc;

import com.example.batch.common.MockBatchTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcCursorItemReaderJobConfigurationTest extends MockBatchTest {

    @Autowired
    JdbcCursorItemReaderJobConfiguration jdbcCursorItemReaderJobConfiguration;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(jdbcCursorItemReaderJobConfiguration.jdbcCursorItemReaderJob());
    }

    @Test
    void jdbcCursorItemReaderJob() {
        // given

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("jdbcCursorItemReaderStep");

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}