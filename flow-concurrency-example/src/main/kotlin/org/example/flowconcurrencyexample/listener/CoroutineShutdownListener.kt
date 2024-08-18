package org.example.flowconcurrencyexample.listener

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

@Component
class CoroutineShutdownListener(
    @Qualifier("globalCustomExecutorService") private val globalCustomExecutorService: ExecutorService,
) : ApplicationListener<ContextClosedEvent> {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    override fun onApplicationEvent(event: ContextClosedEvent) {
        runCatching {
            globalCustomExecutorService.shutdown()
            globalCustomExecutorService.awaitTermination(2, TimeUnit.SECONDS)
            log.info("shutdown executor : $globalCustomExecutorService")
        }.onSuccess {
            log.info("executor service shutdown successful")
        }.onFailure { e ->
            log.error("executor service shutdown failed", e)
        }
    }
}
