package com.example.redisqueueexample.compression

/**
 * A codec ID is persisted in every compressed Redis value and must never be reused.
 * Implementations must encode a self-contained frame and validate size before decompression.
 */
interface CompressionProcessor {
    val codecId: Byte

    fun maxCompressedLength(inputSize: Int): Int

    /** Returns the number of bytes written to [output] at [outputOffset]. */
    fun compress(input: ByteArray): ByteArray

    /** Validates the decoded size before allocating and never returns more than [maxOutputBytes]. */
    fun <T> decompress(input: ByteArray): ByteArray
}
