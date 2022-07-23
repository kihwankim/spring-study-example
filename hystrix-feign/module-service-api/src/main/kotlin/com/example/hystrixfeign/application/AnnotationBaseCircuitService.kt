package com.example.hystrixfeign.application

import com.example.hystrixfeign.adapater.CallApiClient
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class AnnotationBaseCircuitService(
    private val callApiClient: CallApiClient,
) : ApiService {

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
}