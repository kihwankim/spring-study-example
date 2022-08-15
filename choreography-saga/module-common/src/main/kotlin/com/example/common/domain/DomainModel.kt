package com.example.common.domain

interface DomainModel<T, I> {
    fun getId(): I

    fun <E : DomainModel<T, I>> isSameAs(other: E) = other.getId() == this.getId()
}