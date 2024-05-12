package org.example.callee.controller

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TestAdviceController {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handle(): String {
        return "Error"
    }
}