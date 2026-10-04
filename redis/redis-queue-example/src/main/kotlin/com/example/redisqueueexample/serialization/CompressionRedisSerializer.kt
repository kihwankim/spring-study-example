package com.example.redisqueueexample.serialization

import com.example.redisqueueexample.compression.CompressionEnvelope
import com.example.redisqueueexample.compression.CompressionProcessor
import org.slf4j.LoggerFactory
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException

class CompressionRedisSerializer<T : Any>(
    private val deligate: RedisSerializer<T>,
    private val minSizeForCompression: Int,
    private val compressionProcessor: CompressionProcessor,
) : RedisSerializer<T> {

    override fun serialize(value: T?): ByteArray {
        val serializedData = deligate.serialize(value)
        if ((0 < minSizeForCompression && serializedData.size < minSizeForCompression) || isCompressed(serializedData)) {
            return serializedData
        }
        return try {
            compressionProcessor.compress(serializedData)
        } catch (ex: SerializationException) {
            log.warn("[압축처리 이슈] 압축 처리 실패하였습니다.", ex)
            serializedData
        }
    }

    private fun isCompressed(serializedDate: ByteArray): Boolean = CompressionEnvelope.isCompressed(serializedDate)

    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) return null
        if (isCompressed(bytes)) {
            try {
                val decompressedData = compressionProcessor.decompress<T>(bytes)
                return this.deligate.deserialize(decompressedData)
            } catch (ex: SerializationException) {
                throw IllegalStateException("Decompresion failed.", ex)
            }
        }

        return this.deligate.deserialize(bytes)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CompressionRedisSerializer::class.java)
    }
}
