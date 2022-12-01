package com.example.serializerexample.domain

import kotlinx.serialization.Serializable

@Serializable
data class NewColumn(
    val id: Long = 0L,
    val name: String = "",
    val newColumn: String = ""
)