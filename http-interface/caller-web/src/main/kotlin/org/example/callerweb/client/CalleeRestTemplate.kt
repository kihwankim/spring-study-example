package org.example.callerweb.client

import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.util.UriBuilderFactory

interface CalleeRestTemplate {

    @GetExchange("/callee")
    fun calleeCall(factory: UriBuilderFactory): String

    @GetExchange("/error")
    fun calleeError(factory: UriBuilderFactory): String
}