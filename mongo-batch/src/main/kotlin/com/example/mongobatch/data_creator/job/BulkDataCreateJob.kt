package com.example.mongobatch.data_creator.job

import com.example.mongobatch.data_creator.tasklet.BulkDataCreatorTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoTransactionManager

@Configuration
class BulkDataCreateJob(
    private val jobRepository: JobRepository,
    private val mongoTransactionManager: MongoTransactionManager,
) {
    companion object {
        private const val JOB_NAME = "bulkDataCreateAllJob"
    }

    @Bean
    fun bulkDataCreateStep(
        tasklet: BulkDataCreatorTasklet,
    ): Step {
        return StepBuilder("bulkDataCreateStep", jobRepository)
            .tasklet(tasklet, mongoTransactionManager)
            .build()
    }

    @Bean(name = [JOB_NAME])
    fun bulkDataCreateAllJob(
        bulkDataCreateStep: Step,
    ): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .incrementer(RunIdIncrementer())
            .start(bulkDataCreateStep)
            .build()
    }
}