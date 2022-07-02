package com.example.kotlinmultimodule.common.dto.respose

import com.example.kotlinmultimodule.error.ErrorCode

data class ApiResponse<T>(
    val code: String = "",
    val message: String = "",
    val data: T?
) {
    companion object {
        val OK = ApiResponse(data = "OK")

        fun <T> ok(data: T): ApiResponse<T> {
            return ApiResponse(data = data)
        }

        fun error(errorCode: ErrorCode): ApiResponse<Nothing> {
            return ApiResponse(
                code = errorCode.code,
                message = errorCode.message,
                data = null
            )
        }
    }
}