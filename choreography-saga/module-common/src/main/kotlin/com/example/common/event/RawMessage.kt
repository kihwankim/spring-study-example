package com.example.common.event

import com.fasterxml.jackson.annotation.JsonIgnore

interface RawMessage<ID> {
    @JsonIgnore
    fun getEventIdentifier(): ID

    @JsonIgnore
    fun getEventType(): String

    @JsonIgnore
    fun getEventPayload(): String

    @JsonIgnore
    fun getEventHashKey(): String
}