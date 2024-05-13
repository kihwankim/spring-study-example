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
        return calleeClient.calleeCall().block()!!.also { log.info("!!!! call finished") }
    }
}