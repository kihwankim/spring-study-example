package com.example.serializerexample.domain

import kotlinx.serialization.Serializable

interface Upper<ID> {
    fun getIdentifier(): ID
}

interface Middle<ID> : Upper<ID> {
    fun middleFun(): String
}

@Serializable
data class SubClass(
    var id: Long,
    var name: String,
) : Middle<Long> {
    override fun getIdentifier(): Long = id
    override fun middleFun(): String = name
}