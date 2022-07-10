package com.example.hystrixfeign.application

import com.example.hystrixfeign.adapater.CallApiClient
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class AnnotationBaseCircuitService(
    private val callApiClient: CallApiClient,
) : ApiService {

    @CircuitBreaker(name = CIRCUIT_NAME, fallbackMethod = "handleNormal")
    override fun callNoraml() {
        callApiClient.calledData()
    }

    private fun handleNormal(t: Throwable) {
        logger.info { "handle normal" }
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
    override fun callNotFound(): String {
        return callApiClient.callFiled()
    }

    private fun handleNotFound(t: Throwable): String {
        logger.info { "recover not found" }
        return "recover not found"
    }
}