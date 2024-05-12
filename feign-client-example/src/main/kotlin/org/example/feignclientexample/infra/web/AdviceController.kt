package org.example.feignclientexample.infra.web

import org.example.feignclientexample.commons.enums.ErrorType
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
        .status(e.statusCode)
        .body(
            ErrorResponse(
                errorCode = e.errorCode,
                message = e.errorMessage
            )
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Exception): ErrorResponse {
        return ErrorResponse(
            errorCode = ErrorType.INTERNAL_SERVER_ERROR.name,
            message = ErrorType.INTERNAL_SERVER_ERROR.message
        )
    }
}