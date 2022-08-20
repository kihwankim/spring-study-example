package com.example.common.event

import com.fasterxml.jackson.annotation.JsonIgnore

interface DomainEvent<ID> {
    @JsonIgnore
    fun getId(): ID
}