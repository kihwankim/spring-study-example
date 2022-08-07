package com.example.batch.reader.hibernate;

import com.example.batch.entity.student.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HibernateCursorItemReaderJobConfig {
    public static final String JOB_NAME = "hibernateCursorItemReaderJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SessionFactory sessionFactory;

    private int chunkSize;

    @Value("${chunkSize:5}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean(name = JOB_NAME + "_step")
    public Step step() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<Teacher, Teacher>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @StepScope
    @Bean(name = JOB_NAME + "_reader")
    public HibernateCursorItemReader<Teacher> reader() {
        return new HibernateCursorItemReaderBuilder<Teacher>()
                .sessionFactory(sessionFactory)
                .queryString("SELECT t FROM Teacher t")
                .name(JOB_NAME + "_reader")
                .fetchSize(chunkSize)
                .useStatelessSession(false)
                .build();
    }

    public ItemProcessor<Teacher, Teacher> processor() {
        return teacher -> {
            log.info("students count={}", teacher.getStudents().size());
            return teacher;
        };
    }

    private ItemWriter<Teacher> writer() {
        return list -> {
            for (Teacher teacher : list) {
                log.info("Current Teacher={}", teacher);
            }
        };
    }
}
