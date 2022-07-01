package com.example.redissonlock.app.application

import mu.KotlinLogging
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Service
class RedissonLockService(
    private val redissonClient: RedissonClient
) {
    fun testLock(key: String) {
        val locakName = "$key:lock"
        val lock: RLock = redissonClient.getLock(locakName)

        try {
            if (!lock.tryLock(1, 30, TimeUnit.SECONDS)) return
            bizLogic(Thread.currentThread().name)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            if (lock.isLocked) lock.unlock()
        }
    }

    private fun bizLogic(threadName: String) {
        logger.info("$threadName: biz logic")
    }
}