package org.example.feignclientexample.infra.web

import org.example.feignclientexample.commons.exceptions.AppException
import org.example.feignclientexample.web.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(AppException::class)
    fun handleCustomException(e: AppException): ResponseEntity<ErrorResponse> = ResponseEntity
        .status(e.errorType.code)
        .body(
            ErrorResponse(
                errorCode = e.errorType.name,
                message = e.errorType.message
            )
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Exception): ErrorResponse {
        return ErrorResponse(
            errorCode = "error code",
            message = "error message"
        )
    }
}