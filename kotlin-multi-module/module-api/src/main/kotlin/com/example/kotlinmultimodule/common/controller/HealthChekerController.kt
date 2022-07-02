package com.example.kotlinmultimodule.common.controller

import com.example.kotlinmultimodule.common.dto.respose.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthChekerController {
    @GetMapping("/health")
    fun checkServerHealth(): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity.ok(ApiResponse.OK)
    }
}