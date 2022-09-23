package com.example.serializerexample.domain

import kotlinx.serialization.Serializable


@Serializable
data class Movie(
    val title: String,
    val director: String,
    val rating: Double = 1.0
)