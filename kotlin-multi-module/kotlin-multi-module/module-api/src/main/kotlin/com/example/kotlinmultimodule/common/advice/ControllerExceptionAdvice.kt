package com.example.kotlinmultimodule.common.advice

import com.example.kotlinmultimodule.common.dto.respose.ApiResponse
import com.example.kotlinmultimodule.exception.NotFoundException
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionAdvice {
    @ExceptionHandler(NotFoundException::class)
    private fun handleBaseNotFoundException(exception: NotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        logger.info(exception.message, exception)
        return ResponseEntity
            .status(exception.errorCode.status)
            .body(ApiResponse.error(exception.errorCode))
    }
}