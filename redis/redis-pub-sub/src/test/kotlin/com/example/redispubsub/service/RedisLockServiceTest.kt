package com.example.redispubsub.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RedisLockServiceTest {

    @Autowired
    lateinit var redisLockService: RedisLockService

    @Test
    fun `spin lock 테스트`() {
        redisLockService.spinLockExample()
    }
}