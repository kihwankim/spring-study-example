package org.example.callerflux.client

import org.springframework.web.service.annotation.GetExchange
import reactor.core.publisher.Mono

interface CalleeClient {

    @GetExchange("/callee")
    fun calleeCall(): Mono<String>

    @GetExchange("/error")
    fun calleeError(): Mono<String>

    @GetExchange("/callee")
    fun calleeCallBlock(): String

    @GetExchange("/error")
    fun calleeErrorBlock(): String
}