package org.example.callerflux.client

import org.springframework.web.service.annotation.GetExchange
import reactor.core.publisher.Mono

interface CalleeClient {

    @GetExchange("/callee")
    fun calleeCall(): Mono<String>

    @GetExchange("/error")
    fun calleeError(): Mono<String>
}