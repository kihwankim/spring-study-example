package com.example.hystrixfeign.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker
import org.springframework.cloud.client.circuitbreaker.Customizer
import java.time.Duration

@SpringBootTest
internal class CircuitBreakerTest {

    @Autowired
    lateinit var resilience4JCircuitBreakerFactory: Resilience4JCircuitBreakerFactory

    lateinit var circuitBreaker: CircuitBreaker
    private val RING_BUFFER_SIZE_IN_CLOSE_STATE = 20
    private val RING_BUFFER_SIZE_IN_HALF_OPEN = 10
    private val WAIT_DURATION_IN_OPEN_STATE = 5L
    private val FAILURE_RATE_THRESHOLD = 80f
    private val SLIDING_WINDOW_SIZE = 20

    @BeforeEach
    fun setUp() {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(FAILURE_RATE_THRESHOLD)
            .slidingWindowSize(SLIDING_WINDOW_SIZE)
            .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
            .permittedNumberOfCallsInHalfOpenState(RING_BUFFER_SIZE_IN_HALF_OPEN)
            .slidingWindow(RING_BUFFER_SIZE_IN_CLOSE_STATE, RING_BUFFER_SIZE_IN_CLOSE_STATE, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .build()

        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(2))
            .build()

        val customizer = Customizer<Resilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault { id ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig)
                    .circuitBreakerConfig(circuitBreakerConfig)
                    .build()
            }
        }

        customizer.customize(resilience4JCircuitBreakerFactory)
        circuitBreaker = resilience4JCircuitBreakerFactory.create("circuitbreaker")
    }

    @Test
    fun `circuit breaker Test`() {
        // given

        // when

        // then
    }
}