package org.example.flowconcurrencyexample.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
class CoroutineConfig {
    @Bean
    fun globalCustomExecutorService(): ExecutorService {
        return Executors.newFixedThreadPool(
            1_000,
            CustomizableThreadFactory("custom-executor-")
        )
    }

    @Bean
    fun globalCustomCoroutineDispatcher(): CoroutineDispatcher {
        return globalCustomExecutorService().asCoroutineDispatcher()
    }

    @Bean
    fun globalCustomCoroutineScope(): CoroutineScope {
        return CoroutineScope(globalCustomCoroutineDispatcher())
    }

    @Bean
    fun globalCustomCoroutineSupervisorScope(): CoroutineScope {
        return CoroutineScope(globalCustomCoroutineDispatcher() + SupervisorJob())
    }
}