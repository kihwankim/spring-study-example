package com.example.hystrixfeign.adapater

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "test-api", url = "http://localhost:8081")
interface CallApiClient {
    @GetMapping(name = "/called")
    fun calledData()

    @GetMapping("/timeout")
    fun calledTimeOut(): String

    @GetMapping("/fail")
    fun callFiled(): String
}