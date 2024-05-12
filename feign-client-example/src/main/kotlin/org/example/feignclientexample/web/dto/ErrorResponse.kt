package org.example.feignclientexample.web.dto

data class ErrorResponse(
    val errorCode: String,
    val message: String,
)