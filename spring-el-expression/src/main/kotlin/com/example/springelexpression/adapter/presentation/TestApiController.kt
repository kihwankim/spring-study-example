package com.example.springelexpression.adapter.presentation

import com.example.springelexpression.application.domain.SpelTestService
import com.example.springelexpression.application.dto.Hello
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestApiController(
    private val spelTestService: SpelTestService
) {

    @GetMapping("/api/hello")
    fun hello(@RequestParam("id") id: Long, @RequestParam("content") content: String): ResponseEntity<Hello> {
        val response = spelTestService.runWithOneSpelEpxression(Hello(id, content))

        return ResponseEntity.ok(response)
    }
}