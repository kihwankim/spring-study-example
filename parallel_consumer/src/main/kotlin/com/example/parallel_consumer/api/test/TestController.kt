package com.example.parallel_consumer.api.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/api/ping")
    fun ping() = "pong"
}