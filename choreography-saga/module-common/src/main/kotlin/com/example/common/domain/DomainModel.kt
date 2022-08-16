package com.example.common.domain

interface DomainModel<T, I> {
    fun domainModelId(): I

    fun <E : DomainModel<T, I>> isSameAs(other: E) = other.domainModelId() == this.domainModelId()
}