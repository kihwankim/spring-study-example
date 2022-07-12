package com.example.hystrixfeign.api

import com.example.hystrixfeign.application.AnnotationBaseCircuitService
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/annotation")
class AnnotataionEndPointController(
    private val annotationBaseCircuitService: AnnotationBaseCircuitService
) {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        annotationBaseCircuitService.callNoraml()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/timeout")
    fun callTimeOutServer(): ResponseEntity<String> {
        return ResponseEntity.ok(annotationBaseCircuitService.callTimeout())
    }

    @GetMapping("/fail")
    fun callFailServer(): ResponseEntity<String> {
        return ResponseEntity.ok(annotationBaseCircuitService.callNotFound())
    }

    @GetMapping("/rate-limit")
    @RateLimiter(name = "test", fallbackMethod = "rateLimiterFallback")
    fun callRateLimit(): ResponseEntity<String> {
        Thread.sleep(500)
        return ResponseEntity.ok("1234")
    }

    fun rateLimiterFallback(t: Throwable): ResponseEntity<String> {
        return ResponseEntity.ok("failed")
    }
}