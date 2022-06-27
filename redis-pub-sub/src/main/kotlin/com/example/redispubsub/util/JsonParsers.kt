package com.example.redispubsub.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

private val objectMapper = ObjectMapper().registerModule(kotlinModule())

fun toJson(value: Any): String {
    return objectMapper.writeValueAsString(value)
}

fun <T> fromByteArray(src: ByteArray, valueType: Class<T>): T {
    return objectMapper.readValue(src, valueType)
}

fun <T> fromJson(content: String, valueType: Class<T>): T {
    return objectMapper.readValue(content, valueType)
}