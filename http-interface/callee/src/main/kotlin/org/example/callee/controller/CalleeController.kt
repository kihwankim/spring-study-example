package org.example.callee.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class CalleeController {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @GetMapping("/callee")
    fun callee(): Mono<String> {
        Thread.sleep(1_000L)
        return Mono.just("owa").doOnNext {
            log.info("callee call!!")
        }
    }

    @GetMapping("/error")
    fun makeError(): Mono<String> {
        return Mono.error<String>(IllegalArgumentException("error"))
            .doOnError {
                log.info("callee call!!")
            }
    }
}