package com.example.hystrixfeign.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterRegistry
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import mu.KotlinLogging
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

val log = KotlinLogging.logger { }

@Configuration
class Resilience4JConfig {
    @Bean
    fun globalCustomConfiguration(): Customizer<Resilience4JCircuitBreakerFactory> {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(2.0f)
            .waitDurationInOpenState(Duration.ofMillis(10_000)) // 10초
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(2)
            .build()

        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(2))
            .build()

        return Customizer<Resilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault { id ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig)
                    .circuitBreakerConfig(circuitBreakerConfig)
                    .build()
            }
        }
    }

    @Bean
    fun rateLimiterRegistry(): RateLimiterRegistry {
        val rateLimiterRegistry = RateLimiterRegistry.ofDefaults()
        rateLimiterRegistry.eventPublisher
            .onEntryAdded { entryAddedEvent ->
                val addedRatelimiter: RateLimiter = entryAddedEvent.addedEntry
                log.info("RateLimiter {} added", addedRatelimiter.name)
            }
            .onEntryRemoved { entryRemovedEvent ->
                val addedRatelimiter: RateLimiter = entryRemovedEvent.removedEntry
                log.info("RateLimiter {} removed", addedRatelimiter.name)
            }

        return rateLimiterRegistry
    }
}