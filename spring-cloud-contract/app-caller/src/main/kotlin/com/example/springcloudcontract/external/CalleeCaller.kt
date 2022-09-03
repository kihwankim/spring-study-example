package com.example.springcloudcontract.external

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "calleeCaller",
    url = "\${callee.base-url}",
    primary = false,
)
interface CalleeCaller {
    @GetMapping("/api/v1/callee")
    fun callCalleeApplication(@RequestParam num: Int): ResponseEntity<String>
}