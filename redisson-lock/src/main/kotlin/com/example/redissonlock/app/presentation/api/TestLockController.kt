package com.example.redissonlock.app.presentation.api

import com.example.redissonlock.app.application.RedissonLockService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestLockController(
    private val redissonLockService: RedissonLockService
) {
    @GetMapping("/lock/{name}")
    fun getLock(@PathVariable("name") name: String): ResponseEntity<Unit> {
        redissonLockService.testLock(name)

        return ResponseEntity.ok().build()
    }
}