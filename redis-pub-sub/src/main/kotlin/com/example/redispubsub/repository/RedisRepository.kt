package com.example.redispubsub.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun setNx(keyVal: String, ttlVal: Long, value: String): Boolean {
        val isSetVal = redisTemplate.execute { redisConnection ->
            val redisSerializer = redisTemplate.stringSerializer
            redisConnection.setNX(redisSerializer.serialize(keyVal)!!, redisSerializer.serialize(value)!!)
        }!!

        if (isSetVal) {
            redisTemplate.expire(keyVal, ttlVal, TimeUnit.SECONDS)
        }

        return isSetVal
    }

    fun delByKey(deleteKey: String) {
        redisTemplate.execute { redisConnection ->
            val redisSerializer = redisTemplate.stringSerializer
            val key: ByteArray = redisSerializer.serialize(deleteKey)!!
            redisConnection.del(key)
        }
    }
}