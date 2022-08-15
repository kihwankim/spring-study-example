package com.example.common.exception

data class PayServiceCallException(
    val errorMessage: String = "pay server 호출 실패"
) : RuntimeException(errorMessage)