package com.example.redispubsub.pub

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class LockPublisher(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun publishLock(lockTopic: ChannelTopic, lockId: String) {
        val channel: String = lockTopic.topic
        redisTemplate.convertAndSend(channel, lockId)
    }
}