package org.example.callerweb.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class CalleeClient(
    private val calleeWebClient: WebClient,
) {

    fun calleeCall(): Mono<String> {
        return calleeWebClient.get()
            .uri("/callee")
            .retrieve()
            .bodyToMono<String>()
    }
}