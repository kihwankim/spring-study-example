package com.example.hystrixfeign.adapater

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "test-api", url = "http://localhost:8081")
interface CallApi {
    @GetMapping("/called")
    fun calledData()
}