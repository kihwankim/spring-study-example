package com.example.orderapi.common.model

interface DomainModel<E, I> {
    fun getId(): I
}