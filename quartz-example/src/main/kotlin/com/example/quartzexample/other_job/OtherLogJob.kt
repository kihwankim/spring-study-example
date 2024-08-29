package com.example.quartzexample.other_job

import mu.KotlinLogging
import org.quartz.Job
import org.quartz.JobExecutionContext

class OtherLogJob : Job {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    override fun execute(context: JobExecutionContext) {
        val jobName = context.jobDetail.key.name
        log.info("Job Name: $jobName")
        log.info("job data map: ${context.jobDetail.jobDataMap.toMap()}")
    }
}