package org.example.feignclientexample.web.dto

data class ErrorMessage(
    val errorCode: String,
    val message: String,
)