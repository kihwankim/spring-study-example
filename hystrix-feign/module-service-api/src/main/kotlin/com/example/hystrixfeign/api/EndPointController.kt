package com.example.hystrixfeign.api

import com.example.hystrixfeign.adapater.CallApiClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EndPointController(
    private val callApiClient: CallApiClient,
) {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        callApiClient.calledData()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/timeout")
    fun callTimeOutServer(): ResponseEntity<Unit> {
        callApiClient.calledTimeOut()
        return ResponseEntity.ok().build()
    }
}