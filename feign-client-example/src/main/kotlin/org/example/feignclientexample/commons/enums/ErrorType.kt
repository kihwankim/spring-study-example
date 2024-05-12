package org.example.feignclientexample.commons.enums

import org.springframework.http.HttpStatus

enum class ErrorType(
    val message: String,
    val code: Int,
) {
    BAD_GATEWAY(
        message = "BadGateway Error",
        code = HttpStatus.BAD_GATEWAY.value()
    ),
    INTERNAL_SERVER_ERROR(
        message = "Internal Server Error",
        code = HttpStatus.INTERNAL_SERVER_ERROR.value()
    ),
    ;
}