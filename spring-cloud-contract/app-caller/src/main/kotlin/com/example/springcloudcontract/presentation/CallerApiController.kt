package com.example.springcloudcontract.presentation

import com.example.springcloudcontract.external.CalleeCaller
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CallerApiController(
    private val calleeCaller: CalleeCaller,
) {
    @GetMapping("/api/v1/caller")
    fun callCallee(@RequestParam num: Int): ResponseEntity<ResponseBody> {
        val calleeResult = calleeCaller.callCalleeApplication(num)

        return ResponseEntity.ok(ResponseBody(calleeResult.statusCode.value(), calleeResult.body!!))
    }
}

data class ResponseBody(
    val code: Int,
    val result: String
)