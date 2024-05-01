package com.example.coroutineaopexample.service

import com.example.coroutineaopexample.annotation.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TestService {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Logging
    suspend fun callSuspendService(): String {
        log.info("run data")

        return "Hello World"
    }

    @Logging
    suspend fun callFlowSuspendService(): Flow<String> {
        log.info("run data")
        return flow {
            emit("Hello")
            emit(" World")
        }
    }

    @Logging
    fun callReactor(): Mono<String> {
        log.info("run reactor")

        return Mono.just("Hello World")
    }
}