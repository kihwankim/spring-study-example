package com.example.common.event

interface RawMessage<ID> {
    fun getId(): ID

    fun getType(): String

    fun getPayload(): String

    fun hashKey(): String
}