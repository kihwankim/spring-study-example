package com.example.redispubsub.pub

import com.example.redispubsub.dto.RoomChat
import com.example.redispubsub.util.toJson
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RoomChatPub(
    private val redisTemplate: RedisTemplate<String, String>,
    private val roomChatChannelTopic: ChannelTopic,
) {

    fun publish(roomChat: RoomChat) {
        val channel: String = roomChatChannelTopic.topic
        val message: String = toJson(roomChat)
        redisTemplate.convertAndSend(channel, message)
    }
}