package com.example.hystrixfeign

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CalledController {
    @GetMapping("/called")
    fun called(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}