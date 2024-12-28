package org.example.callerweb.client

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.util.UriBuilderFactory

@Component
class CalleePureRestTeamplate(
    private val restTemplate: RestTemplate,
    private val localUriBuilderFactory: UriBuilderFactory,
    private val restClientCircuitBreaker: CircuitBreaker
) {

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "restClientCircuitBreaker")
    fun callRestTemplate(): String? {
        return restClientCircuitBreaker.executeSupplier {
            restTemplate.getForEntity<String>(
                localUriBuilderFactory.builder()
                    .path("/callee")
                    .build()
            )
        }.body
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "restClientCircuitBreaker")
    fun callJsonTemplate(): TestBody? {
        return restTemplate.getForEntity<TestBody>(
            localUriBuilderFactory.builder()
                .path("/json")
                .build()
        ).body
    }
}

data class TestBody(
    val name: String,
)