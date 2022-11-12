package com.example.redispubsub.service

import com.example.redispubsub.repository.RedisRepository
import org.springframework.stereotype.Service


@Service
class RedisLockService(
    private val redisRepository: RedisRepository
) {

    fun spinLockExample() {
        val lockKey = "lock"
        val lockTTL = 30L
        val lockValue = "value"

        try {
            while (!redisRepository.setNx(lockKey, lockTTL, lockValue)) {
                runVal()
            }

            redisRepository.delByKey(lockKey)
        } catch (_: Exception) {
        }
    }

    private fun runVal() {
        var i = 0
        while (i < 3) {
            Thread.sleep(2000)
            println("sleep")

            i++
        }
    }
}