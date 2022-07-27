package com.example.hystrixfeign.api

import com.example.hystrixfeign.application.AnnotationBaseCircuitService
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
    fun callRateLimit(): ResponseEntity<String> {
        val callRateLimit = annotationBaseCircuitService.callRateLimit()
        return ResponseEntity.ok(callRateLimit)
    }

    @GetMapping("/retry")
    fun callRetry(): ResponseEntity<String> {
        val callRetryResult = annotationBaseCircuitService.callRetry()

        return ResponseEntity.ok(callRetryResult)
    }
}