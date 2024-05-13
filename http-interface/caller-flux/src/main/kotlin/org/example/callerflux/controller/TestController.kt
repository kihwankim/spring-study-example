package org.example.callerflux.controller

import mu.KotlinLogging
import org.example.caller.client.CalleeClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController(
    private val calleeClient: CalleeClient,
) {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/call")
    fun callData(): Mono<String> {
        log.info("!!!call!!!!")
        return calleeClient.calleeCall().doOnNext {
            log.info("!!!! call finished")
        }
    }
}