package com.example.common.event

interface RawMessage<ID> {
    fun eventIdentifier(): ID

    fun eventType(): String

    fun eventRawMessagePayload(): String

    fun eventHashKeyValue(): String
}