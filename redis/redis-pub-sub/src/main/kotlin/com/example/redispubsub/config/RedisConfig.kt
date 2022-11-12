package com.example.redispubsub.config

import com.example.redispubsub.sub.RoomChatSub
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}") private val host: String,
    @Value("\${spring.redis.port}") private val port: Int,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.setConnectionFactory(redisConnectionFactory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        return template
    }

    @Bean
    fun roomChatChannelTopic(): ChannelTopic {
        return ChannelTopic("room.chat")
    }


    @Bean
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        roomChatSubscriber: RoomChatSub,
        roomChatChannelTopic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()

        container.setConnectionFactory(redisConnectionFactory)
        container.addMessageListener(roomChatSubscriber, roomChatChannelTopic)

        return container
    }
}