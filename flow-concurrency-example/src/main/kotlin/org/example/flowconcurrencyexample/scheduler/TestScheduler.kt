package org.example.flowconcurrencyexample.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestScheduler {
    @Scheduled(fixedDelay = 1_000)
    fun runThreadPool() {

    }
}