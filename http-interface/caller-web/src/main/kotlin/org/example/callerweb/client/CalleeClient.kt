package org.example.callerweb.client

import org.springframework.web.service.annotation.GetExchange

interface CalleeClient {

    @GetExchange("/callee")
    fun calleeCall(): String

    @GetExchange("/error")
    fun calleeError(): String
}