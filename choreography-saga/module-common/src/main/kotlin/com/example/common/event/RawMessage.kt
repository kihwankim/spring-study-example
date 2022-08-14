package com.example.common.event

interface RawMessage<ID> {
    fun getId(): ID

    fun getType(): String

    fun getVersion(): Long

    fun getPayload(): String

    fun hashKey(): String
}