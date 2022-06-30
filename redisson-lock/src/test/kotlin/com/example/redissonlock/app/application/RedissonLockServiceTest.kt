package com.example.redissonlock.app.application

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RedissonLockServiceTest {
    @Autowired
    lateinit var redissonLockService: RedissonLockService

    @Test
    fun `lock 확득 테스트 - 성공`() {
        redissonLockService.testLock("key")
    }
}