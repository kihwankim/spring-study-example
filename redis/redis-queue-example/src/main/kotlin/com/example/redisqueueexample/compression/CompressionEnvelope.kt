package com.example.redisqueueexample.compression

object CompressionEnvelope {
    const val HEADER_SIZE = 5
    private val PREFIX = byteArrayOf(0, 'R'.code.toByte(), 'Q'.code.toByte(), 1)

    fun isCompressed(bytes: ByteArray): Boolean =
        bytes.size >= HEADER_SIZE && PREFIX.indices.all { bytes[it] == PREFIX[it] }

    fun writeHeader(output: ByteArray, codecId: Byte) {
        PREFIX.copyInto(output)
        output[PREFIX.size] = codecId
    }
}
