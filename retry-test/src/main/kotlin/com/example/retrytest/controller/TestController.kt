package com.example.retrytest.controller

import com.example.retrytest.caller.TestCaller
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testCaller: TestCaller
) {

    @GetMapping("/v1/test")
    fun callTest(@RequestParam isTest: Boolean) = ResponseEntity.ok(testCaller.callAll(isTest))
}