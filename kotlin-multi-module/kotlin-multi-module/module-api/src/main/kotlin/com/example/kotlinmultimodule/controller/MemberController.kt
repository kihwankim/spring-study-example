package com.example.kotlinmultimodule.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {
    @GetMapping("/health")
    fun healthChecker(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}