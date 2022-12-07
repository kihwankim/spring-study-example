package com.example.retrytest.callee

import mu.KotlinLogging
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class TestCallee {

    @Retryable(backoff = Backoff(delay = 50L, multiplier = 2.0, maxDelay = 1000), maxAttempts = 2, value = [IllegalStateException::class])
    fun errorTest(): Int {
        logger.info("run data")
        throw IllegalStateException()
    }

    @Retryable(backoff = Backoff(delay = 50L, multiplier = 2.0, maxDelay = 1000), maxAttempts = 2, value = [IllegalStateException::class])
    fun errorOtherTest(): Int {
        logger.info("run data")
        throw IllegalStateException()
    }
}