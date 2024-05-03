package org.example.feignclientexample.infra.web

import org.example.feignclientexample.web.dto.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Exception): ErrorMessage {
        return ErrorMessage(
            errorCode = "error code",
            message = "error message"
        )
    }
}