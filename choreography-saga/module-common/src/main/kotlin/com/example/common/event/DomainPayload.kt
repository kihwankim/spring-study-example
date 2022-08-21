package com.example.common.event

import com.fasterxml.jackson.annotation.JsonIgnore

interface DomainPayload<ID> {
    @JsonIgnore
    fun getId(): ID
}