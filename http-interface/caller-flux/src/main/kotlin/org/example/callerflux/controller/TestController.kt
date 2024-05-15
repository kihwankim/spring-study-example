package org.example.callerflux.controller

import mu.KotlinLogging
import org.example.callerflux.client.CalleeClient
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

    @GetMapping("/error")
    fun callError(): Mono<String> {
        log.info("!!!call Error!!!!")
        return calleeClient.calleeError().doOnError {
            log.info("!!!! call Error finished")
        }
    }

    @GetMapping("/call-block")
    fun callDataBlock(): String {
        log.info("!!!call Block!!!!")
        return calleeClient.calleeCallBlock()
    }

    @GetMapping("/error-block")
    fun callErrorBlock(): String {
        log.info("!!!call Error Block!!!!")
        return calleeClient.calleeErrorBlock()
    }
}