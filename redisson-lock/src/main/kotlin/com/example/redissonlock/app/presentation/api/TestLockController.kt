package com.example.redissonlock.app.presentation.api

import com.example.redissonlock.app.application.RedissonLockService
import com.example.redissonlock.app.presentation.dto.LockRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestLockController(
    private val redissonLockService: RedissonLockService
) {
    @GetMapping("/lock")
    fun getLock(lockRequest: LockRequest): ResponseEntity<Unit> {
        redissonLockService.testLock(lockRequest.lockName, lockRequest.wait, lockRequest.leatTime)

        return ResponseEntity.ok().build()
    }
}