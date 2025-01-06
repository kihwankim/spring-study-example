package com.example.mongobatch.data_creator.tasklet

import com.example.mongobatch.mongo.BatchTestDocument
import com.example.mongobatch.mongo.BatchTestMongoRepository
import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class BulkDataCreatorTasklet(
    private val batchTestMongoRepository: BatchTestMongoRepository,
) : Tasklet {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext
    ): RepeatStatus {
        log.info("!!!! started")
        (1..10000).map {
            BatchTestDocument.createRandomly()
        }.chunked(1000).forEach {
            batchTestMongoRepository.saveAll(it)
        }
        log.info("!!!! finished")

        return RepeatStatus.FINISHED
    }
}