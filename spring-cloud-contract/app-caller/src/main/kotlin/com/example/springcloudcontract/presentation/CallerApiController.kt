package com.example.springcloudcontract.presentation

import com.example.springcloudcontract.service.CallService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CallerApiController(
    private val callService: CallService,
) {
    @GetMapping("/api/v1/caller")
    fun callCallee(@RequestParam num: Int): ResponseEntity<ResponseBody> {
        return ResponseEntity.ok(callService.callData(num))
    }
}

data class ResponseBody(
    val code: Int,
    val result: String
)