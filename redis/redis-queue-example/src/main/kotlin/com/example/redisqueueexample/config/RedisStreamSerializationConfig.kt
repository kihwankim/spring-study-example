package com.example.redisqueueexample.config

import com.example.redisqueueexample.compression.ZstdCompressionProcessor
import com.example.redisqueueexample.domain.TestData
import com.example.redisqueueexample.serialization.CompressionRedisSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import tools.jackson.databind.ObjectMapper

@Configuration
class RedisStreamSerializationConfig {
    @Bean
    fun testDataSerializer(
        objectMapper: ObjectMapper,
    ): CompressionRedisSerializer<TestData> {
        val compressionProcessor = ZstdCompressionProcessor()
        return CompressionRedisSerializer(
            JacksonJsonRedisSerializer(objectMapper, TestData::class.java),
            256,
            compressionProcessor,
        )
    }

    @Bean("binaryStreamRedisTemplate")
    fun binaryStreamRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, ByteArray> =
        RedisTemplate<String, ByteArray>().apply {
            setConnectionFactory(connectionFactory)
            keySerializer = StringRedisSerializer()
            hashKeySerializer = StringRedisSerializer()
            valueSerializer = RedisSerializer.byteArray()
            hashValueSerializer = RedisSerializer.byteArray()
            afterPropertiesSet()
        }
}