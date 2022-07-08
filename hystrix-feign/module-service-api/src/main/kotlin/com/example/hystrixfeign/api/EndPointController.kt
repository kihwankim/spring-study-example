package com.example.hystrixfeign.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EndPointController {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}