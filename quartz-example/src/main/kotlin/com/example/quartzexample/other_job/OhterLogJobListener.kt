package com.example.quartzexample.other_job

import mu.KotlinLogging
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener

class OhterLogJobListener : JobListener {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    // Listener의 이름 정의
    override fun getName(): String = "OhterLogJobListener"

    // Job이 실행되기 전 호출
    override fun jobToBeExecuted(context: JobExecutionContext) {
        log.info("OhterLogJobListener Job is about to be executed: " + context.jobDetail.key)
    }

    // Job 실행이 중단되었을 때 호출
    override fun jobExecutionVetoed(context: JobExecutionContext) {
        log.info("OhterLogJobListener Job execution was vetoed: " + context.jobDetail.key)
    }

    // Job이 실행된 후 호출
    override fun jobWasExecuted(
        context: JobExecutionContext,
        jobException: JobExecutionException?,
    ) {
        log.info("Job was executed: " + context.jobDetail.key)
        if (jobException != null) {
            log.info("OhterLogJobListener Exception thrown by job: " + jobException.message)
        }
    }
}