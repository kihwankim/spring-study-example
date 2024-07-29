package com.example.common.domain

data class ErrorResult(
    val ex: Exception,
) {
    fun throwError() {
        throw ex
    }
}