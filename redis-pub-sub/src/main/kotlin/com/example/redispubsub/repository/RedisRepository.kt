package com.example.redispubsub.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun setNx(keyVal: String, ttlVal: Long, value: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent(keyVal, value, ttlVal, TimeUnit.SECONDS)!!
    }

    fun delByKey(deleteKey: String) {
        redisTemplate.delete(deleteKey)
    }
}