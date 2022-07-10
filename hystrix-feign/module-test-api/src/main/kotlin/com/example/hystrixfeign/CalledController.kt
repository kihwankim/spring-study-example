package com.example.hystrixfeign

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.ServletRequest

private val logger = KotlinLogging.logger {}

@RestController
class CalledController {
    @GetMapping("/called")
    fun called(headers: ServletRequest): ResponseEntity<Unit> {
        logger.info { "called" }
        Thread.sleep(1000)
        logger.info { headers.getAttribute("headerval") }
        return ResponseEntity.ok().build()
    }

    @GetMapping("/timeout")
    fun calledTimeout(): ResponseEntity<String> {
        logger.info { "before sleep 10_000 millis" }
        Thread.sleep(10_000)
        logger.info { "after sleep 10_000 millis" }
        return ResponseEntity.ok("success")
    }

    @GetMapping("/fail")
    fun calledFail(): ResponseEntity<String> {
        logger.info { "fail" }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found")
    }
}