package org.example.callee.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class CalleeController {
    @GetMapping("/callee")
    fun callee(): Mono<String> {
        Thread.sleep(5_000L)
        return Mono.just("owa")
    }

    @GetMapping("/error")
    fun makeError(): Mono<String> {
        return Mono.error(IllegalArgumentException("error"))
    }
}