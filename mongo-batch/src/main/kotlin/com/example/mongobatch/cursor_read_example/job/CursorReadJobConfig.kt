package com.example.mongobatch.cursor_read_example.job

import com.example.mongobatch.cursor_read_example.reader.BulkDataCursorReader
import com.example.mongobatch.mongo.BatchTestDocument
import mu.KotlinLogging
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
class CursorReadJobConfig(
    private val jobRepository: JobRepository,
    private val mongoTransactionManager: MongoTransactionManager,
) {
    companion object {
        private val log = KotlinLogging.logger { }
        private const val JOB_NAME = "cursorReadJob"
    }

    @Bean
    fun cursorReadLogStep(
        bulkDataCursorReader: BulkDataCursorReader,
    ): Step {
        return StepBuilder("cursorReadLogStep", jobRepository)
            .chunk<BatchTestDocument, BatchTestDocument>(1000, mongoTransactionManager)
            .reader(bulkDataCursorReader)
            .writer { chunkedItems ->
                chunkedItems.forEach {
                    log.info("!!! ${it.cursorItem}, ${it.randomId}")
                    if (it.cursorItem == 9219680444340982263L) {
                        log.warn("!!! last")
                    }
                }
            }
            .build()
    }

    @Bean(name = [JOB_NAME])
    fun cursorReadJob(
        cursorReadLogStep: Step,
    ): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .incrementer(RunIdIncrementer())
            .start(cursorReadLogStep)
            .build()
    }
}