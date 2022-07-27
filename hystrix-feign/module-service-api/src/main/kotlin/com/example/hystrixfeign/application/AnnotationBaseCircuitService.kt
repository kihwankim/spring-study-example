package com.example.hystrixfeign.application

import com.example.hystrixfeign.adapater.CallApiClient
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger { }

@Component
class AnnotationBaseCircuitService(
    private val callApiClient: CallApiClient,
    private val rateLimiterRegistry: RateLimiterRegistry,
) : ApiService {

    @PostConstruct
    private fun setUp() {
        rateLimiterRegistry.rateLimiter("test").eventPublisher
            .onSuccess { successEvent -> logger.info { "success: ${successEvent.rateLimiterName} ${successEvent.eventType}" } }
            .onFailure { failedEvent -> logger.info { "failed: ${failedEvent.rateLimiterName} ${failedEvent.eventType}" } }
    }

    @CircuitBreaker(name = CIRCUIT_NAME, fallbackMethod = "handleNormal")
    @Bulkhead(name = CIRCUIT_NAME, type = Bulkhead.Type.SEMAPHORE)
    override fun callNoraml() {
        callApiClient.calledData()
        logger.info { "run success" }
    }

    private fun handleNormal(t: Throwable) {
        logger.info { "handle normal" }
    }

    private fun bulkheadNormalFallback(t: Throwable) {
        logger.info { "recover bulkhead" }
    }

    @CircuitBreaker(name = CIRCUIT_NAME, fallbackMethod = "handleTimeout")
    override fun callTimeout(): String {
        return callApiClient.calledTimeOut()

    }

    private fun handleTimeout(t: Throwable): String {
        logger.info { "Timeout Handle" }
        return "Timeout Handle"
    }

    @CircuitBreaker(name = CIRCUIT_NAME, fallbackMethod = "handleNotFound")
    @Bulkhead(name = CIRCUIT_NAME, fallbackMethod = "bulkheadNotFoundFallback", type = Bulkhead.Type.THREADPOOL)
    override fun callNotFound(): String {
        return callApiClient.callFiled()
    }

    private fun handleNotFound(t: Throwable): String {
        logger.info { "recover not found" }
        return "recover not found"
    }

    private fun bulkheadNotFoundFallback(t: Throwable): String {
        logger.info { "recover bulkhead" }
        return "bulkhead"
    }

    @RateLimiter(name = "test", fallbackMethod = "rateLimiterFallback")
    fun callRateLimit(): String {
        return "1234"
    }

    private fun rateLimiterFallback(t: Throwable): String {
        return "handle"
    }

    @Retry(name = "retry-test", fallbackMethod = "retryFallback")
    fun callRetry(): String {
        logger.info("retry")
        throw Exception("exp")
        return "retry"
    }

    private fun retryFallback(t: Throwable): String {
        return "retry fallback"
    }
}