package com.example.redisqueueexample.compression

import com.github.luben.zstd.Zstd
import org.springframework.data.redis.serializer.SerializationException

class ZstdCompressionProcessor(
    private val compressionLevel: Int = 1,
) : CompressionProcessor {
    override val codecId: Byte = 1

    init {
        require(compressionLevel in 1..22) { "compressionLevel must be between 1 and 22" }
    }

    override fun maxCompressedLength(inputSize: Int): Int {
        val bound = Zstd.compressBound(inputSize.toLong())
        if (bound > Int.MAX_VALUE) throw SerializationException("Redis payload is too large to compress")
        return bound.toInt()
    }

    override fun compress(input: ByteArray): ByteArray {
        val bound = maxCompressedLength(input.size)
        if (bound > Int.MAX_VALUE - CompressionEnvelope.HEADER_SIZE) {
            throw SerializationException("Redis payload is too large to compress")
        }
        val output = ByteArray(CompressionEnvelope.HEADER_SIZE + bound)
        CompressionEnvelope.writeHeader(output, codecId)
        val compressedSize = Zstd.compressByteArray(
            output, CompressionEnvelope.HEADER_SIZE, bound,
            input, 0, input.size, compressionLevel,
        )
        if (Zstd.isError(compressedSize)) {
            throw SerializationException("Zstd compression failed: ${Zstd.getErrorName(compressedSize)}")
        }
        return output.copyOf(CompressionEnvelope.HEADER_SIZE + compressedSize.toInt())
    }

    override fun <T> decompress(input: ByteArray): ByteArray {
        if (!CompressionEnvelope.isCompressed(input) || input[4] != codecId) {
            throw SerializationException("Unknown compressed Redis payload format")
        }
        val frameLength = input.size - CompressionEnvelope.HEADER_SIZE
        if (frameLength == 0) throw SerializationException("Empty compressed Redis payload")

        val originalSize = Zstd.getFrameContentSize(input, CompressionEnvelope.HEADER_SIZE, frameLength)
        if (originalSize !in 1..MAX_DECOMPRESSED_BYTES) {
            throw SerializationException("Invalid or oversized compressed Redis payload")
        }
        val decoded = ByteArray(originalSize.toInt())
        val decodedSize = Zstd.decompressByteArray(
            decoded, 0, decoded.size,
            input, CompressionEnvelope.HEADER_SIZE, frameLength,
        )
        if (Zstd.isError(decodedSize) || decodedSize != originalSize) {
            throw SerializationException("Zstd decompression failed")
        }
        return decoded
    }

    companion object {
        private const val MAX_DECOMPRESSED_BYTES = 16 * 1024 * 1024
    }
}
