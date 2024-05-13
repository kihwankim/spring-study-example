package org.example.callerflux.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class CalleeClient(
    private val calleeWebClient: WebClient,
    private val callee2WebClient: WebClient,
) {

    fun calleeCall(): Mono<String> {
        return calleeWebClient.get()
            .uri("/callee")
            .retrieve()
            .bodyToMono<String>()
    }

    fun callee2Call(): Mono<String> {
        return callee2WebClient.get()
            .uri("/callee")
            .retrieve()
            .bodyToMono<String>()
    }
}