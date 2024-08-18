package org.example.flowconcurrencyexample.scheduler

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.example.flowconcurrencyexample.service.ConcurrentService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestScheduler(
    private val concurrentService: ConcurrentService,
) {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Scheduled(fixedDelay = 20_000)
    fun runThreadPool() {
        val startTime = System.currentTimeMillis()
        val reuslt = runBlocking { // 40 만명 기준
            (0..0).map { index ->
                concurrentService.runBatch(index, (10_000 * index..<10_000 * (index + 1)).toList())
            }.fold(0) { acc: Int, i: Int ->
                acc + i
            }
        }
        log.error("!!!! Batch spend time ${(System.currentTimeMillis() - startTime) / 1_000} s")
        log.error("!!!! result count: $reuslt")
    } // !!!! Batch spend time 926 s
}