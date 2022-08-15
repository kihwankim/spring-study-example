package com.example.common.event

interface DomainEvent<ID> {
    fun getId(): ID
}