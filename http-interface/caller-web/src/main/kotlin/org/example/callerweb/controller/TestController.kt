package org.example.callerweb.controller

import mu.KotlinLogging
import org.example.callerweb.client.CalleeClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val calleeClient: CalleeClient,
) {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/call")
    fun callData(): String {
        log.info("!!!call!!!!")
        return calleeClient.calleeCall().also { log.info("!!!! call finished") }
    }

    @GetMapping("/error")
    fun callError(): String {
        log.info("!!!call Error!!!!")
        return calleeClient.calleeError().also { log.info("!!!! call error finished") }
    }
}