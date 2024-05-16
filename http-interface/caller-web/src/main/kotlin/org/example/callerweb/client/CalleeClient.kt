package org.example.callerweb.client

import org.springframework.web.service.annotation.GetExchange

interface CalleeClient {

    @GetExchange("\${client.local.url}/callee")
    fun calleeCall(): String

    @GetExchange("\${client.local.url}/error")
    fun calleeError(): String
}