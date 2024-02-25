package com.example.coroutineaopexample.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController {

    @GetMapping("/call-suspend-aop")
    suspend fun callAop(): String {
        return "Hello World"
    }

    @GetMapping("/call-reactor")
    fun callReactor(): Mono<String> {
        return Mono.just("Hello World")
    }
}