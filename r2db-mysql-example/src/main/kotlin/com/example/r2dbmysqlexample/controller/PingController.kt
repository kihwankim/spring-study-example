package com.example.r2dbmysqlexample.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class PingController {
    @GetMapping("/pong")
    fun pong(): Mono<String> = Mono.just("pong")
}