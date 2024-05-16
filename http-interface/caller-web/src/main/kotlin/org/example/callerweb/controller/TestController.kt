package org.example.callerweb.controller

import mu.KotlinLogging
import org.example.callerweb.client.CalleeClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilderFactory

@RestController
class TestController(
    private val calleeClient: CalleeClient,
    private val localUriBuilderFactory: UriBuilderFactory,
) {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/call")
    fun callData(): String {
        log.info("!!!call!!!!")
        return calleeClient.calleeCall(localUriBuilderFactory).also { log.info("!!!! call finished") }
    }

    @GetMapping("/error")
    fun callError(): String {
        log.info("!!!call Error!!!!")
        return calleeClient.calleeError(localUriBuilderFactory).also { log.info("!!!! call error finished") }
    }
}