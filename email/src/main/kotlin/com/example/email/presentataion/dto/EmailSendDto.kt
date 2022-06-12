package com.example.email.presentataion.dto

data class EmailSendDto(
    val email: String = "",
    val subject: String = "",
    val header: String = "",
    val value: String = ""
)