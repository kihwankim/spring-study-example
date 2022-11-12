package com.example.redissonlock.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}") private val host: String,
    @Value("\${spring.redis.port}") private val port: Int,
) {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = "redis://$host:$port"

        return Redisson.create(config)
    }
}