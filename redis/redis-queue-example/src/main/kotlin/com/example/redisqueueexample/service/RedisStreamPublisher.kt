package com.example.redisqueueexample.service

import com.example.redisqueueexample.domain.TestData
import com.example.redisqueueexample.serialization.CompressionRedisSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.RecordId
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisStreamPublisher(
    @Qualifier("binaryStreamRedisTemplate") private val redisTemplate: RedisTemplate<String, ByteArray>,
    private val serializer: CompressionRedisSerializer<TestData>,
    @Value("\${app.redis-stream.key}") private val streamKey: String,
) {
    fun publish(data: TestData): RecordId {
        val record = MapRecord.create(streamKey, mapOf("data" to serializer.serialize(data)))
        return requireNotNull(redisTemplate.opsForStream<String, ByteArray>().add(record)) {
            "Redis did not return a stream record ID"
        }
    }
}
