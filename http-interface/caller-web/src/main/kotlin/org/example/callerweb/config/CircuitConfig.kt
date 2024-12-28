package org.example.callerweb.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.ResourceAccessException
import java.net.SocketTimeoutException
import java.time.Duration

@Configuration
class CircuitConfig(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
) {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Bean(name = ["restClientCircuitBreaker"])
    fun restClientCircuitBreaker(): CircuitBreaker = circuitBreakerRegistry.circuitBreaker(
        "restClientCircuitBreaker",
        createRestTemplateCircuitConfig().build(),
    ).logger()

    private fun createRestTemplateCircuitConfig(
        slidingWindowSize: Int = 100,
        mininumNumberOfCalls: Int = 50,
        failRateThreshold: Float = 80F,
        slowDuratinThreshold: Duration = Duration.ofSeconds(3),
        slowRateThreshold: Float = 80F,
        permitCallCountInHalfOpen: Int = 10,
        waitDurationInOpenState: Duration = Duration.ofSeconds(10),
    ): CircuitBreakerConfig.Builder = CircuitBreakerConfig.custom()
        // fail-over
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        .slidingWindowSize(slidingWindowSize)
        .minimumNumberOfCalls(mininumNumberOfCalls)
        .failureRateThreshold(failRateThreshold)
        // time-over
        .slowCallDurationThreshold(slowDuratinThreshold)
        .slowCallRateThreshold(slowRateThreshold)
        // circuit-over
        .automaticTransitionFromOpenToHalfOpenEnabled(true) // open -> halfOpen state로 자동 전환 (false: api 호출 전까지 open 상태 유지)
        .permittedNumberOfCallsInHalfOpenState(permitCallCountInHalfOpen)
        .waitDurationInOpenState(waitDurationInOpenState)
        .ignoreExceptions(IllegalArgumentException::class.java)
        .recordException { e -> // error-condition
            when (e) {
                is HttpClientErrorException -> !e.statusCode.is4xxClientError // 4xx 은 제외
                is HttpServerErrorException -> true // 500 error
                is ResourceAccessException, is SocketTimeoutException -> true // 타임아웃 예외나 네트워크 오류
                else -> false
            }
        }

    /**
     * 서킷 브레이커가 상태를 전환할 때 호출
     * Closed → Open, Open → Half-Open, Half-Open → Closed 감지
     */
    private fun CircuitBreaker.logger(): CircuitBreaker = this.also {
        it.eventPublisher.onStateTransition { event ->
            log.error("circuitBreakerDetect: [${event.stateTransition.fromState}] -> [${event.stateTransition.toState}] status: ${event.stateTransition}, name: ${event.circuitBreakerName}, originMsg:[$event]")
        }
    }
}