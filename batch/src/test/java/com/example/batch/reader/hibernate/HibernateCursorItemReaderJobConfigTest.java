package com.example.batch.reader.hibernate;

import com.example.batch.common.MockBatchTest;
import com.example.batch.entity.product.ProductStatus;
import com.example.batch.entity.student.TeacherRepository;
import com.example.batch.fixture.InitProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HibernateCursorItemReaderJobConfigTest extends MockBatchTest {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    InitProcessor initProcessor;

    @Autowired
    HibernateCursorItemReaderJobConfig hibernateCursorItemReaderJob;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(hibernateCursorItemReaderJob.job());
    }

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
        jobRepositoryTestUtils.removeJobExecutions();
    }


    @Test
    void jobTest() {
        // given
        initProcessor.saveTeacher();
        LocalDate localDate = LocalDate.of(2022, 1, 1);

        ProductStatus status = ProductStatus.APPROVE;
        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("createDate", localDate.toString())
                .addString("status", status.name())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(HibernateCursorItemReaderJobConfig.JOB_NAME + "_step", jobParameters);

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}