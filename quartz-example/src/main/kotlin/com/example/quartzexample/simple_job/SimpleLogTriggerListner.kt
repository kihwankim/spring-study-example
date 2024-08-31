package com.example.quartzexample.simple_job

import mu.KotlinLogging
import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerListener

class SimpleLogTriggerListner : TriggerListener {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    override fun getName(): String = "simpleLogTriggerListener"

    override fun triggerFired(trigger: Trigger, context: JobExecutionContext) {
        log.info("triggerFiredtrigger listener ${trigger.key}")
    }

    override fun vetoJobExecution(trigger: Trigger, context: JobExecutionContext): Boolean {
        log.info("vetoJobExecution trigger listener ${trigger.key}")
        return true
    }

    override fun triggerMisfired(trigger: Trigger) {
        log.info("triggerMisfired listener ${trigger.key}")
    }

    override fun triggerComplete(trigger: Trigger, context: JobExecutionContext?, triggerInstructionCode: Trigger.CompletedExecutionInstruction?) {
        log.info("trigger listener ${trigger.key}")
    }
}