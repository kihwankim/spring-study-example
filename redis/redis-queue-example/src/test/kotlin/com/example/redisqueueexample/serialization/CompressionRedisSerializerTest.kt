package com.example.redisqueueexample.serialization

import com.example.redisqueueexample.compression.ZstdCompressionProcessor
import com.example.redisqueueexample.domain.TestData
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.redis.serializer.SerializationException
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer
import tools.jackson.module.kotlin.jacksonObjectMapper

class CompressionRedisSerializerTest {
    private val jsonSerializer = JacksonJsonRedisSerializer(jacksonObjectMapper(), TestData::class.java)
    private val serializer = CompressionRedisSerializer(jsonSerializer, 256, ZstdCompressionProcessor())

    @Test
    fun `small values remain ordinary JSON and round trip`() {
        val value = TestData(12, "홍길동")
        val bytes = serializer.serialize(value)

        assertTrue(bytes.first() == '{'.code.toByte())
        assertEquals(value, serializer.deserialize(bytes))
    }

    @Test
    fun `large repetitive values are compressed and round trip`() {
        val value = TestData(12, "홍길동".repeat(500))
        val raw = jacksonObjectMapper().writeValueAsBytes(value)
        val bytes = serializer.serialize(value)

        assertTrue(bytes.size < raw.size)
        assertEquals(0.toByte(), bytes.first())
        assertEquals(value, serializer.deserialize(bytes))
    }

    @Test
    fun `compression threshold skips medium sized values`() {
        val highThreshold = CompressionRedisSerializer(jsonSerializer, 4_096, ZstdCompressionProcessor())
        val value = TestData(12, "a".repeat(500))
        val bytes = highThreshold.serialize(value)

        assertTrue(bytes.first() == '{'.code.toByte())
        assertEquals(value, highThreshold.deserialize(bytes))
    }

    @Test
    fun `reads JSON produced before compression was introduced`() {
        val bytes = """{"accountId":42,"name":"legacy"}""".toByteArray()

        assertEquals(TestData(42, "legacy"), serializer.deserialize(bytes))
    }

    @Test
    fun `rejects corrupt compressed data and unknown formats`() {
        val bytes = serializer.serialize(TestData(1, "x".repeat(1_000)))
        val corrupt = bytes.copyOf().also { it[5] = 0xFF.toByte() }

        assertThrows(SerializationException::class.java) { serializer.deserialize(corrupt) }
        assertThrows(SerializationException::class.java) { serializer.deserialize(byteArrayOf(0, 'R'.code.toByte(), 'Q'.code.toByte(), 2, 1, 1)) }
        assertThrows(SerializationException::class.java) { serializer.deserialize(bytes.copyOf().also { it[4] = 99 }) }
    }

    @Test
    fun `handles null and missing values`() {
        assertArrayEquals(byteArrayOf(), serializer.serialize(null))
        assertEquals(null, serializer.deserialize(null))
        assertEquals(null, serializer.deserialize(byteArrayOf()))
    }
}
