package com.example.hystrixfeign.api

import com.example.hystrixfeign.adapater.CallApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EndPointController(
    private val callApi: CallApi
) {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        callApi.calledData()
        return ResponseEntity.ok().build()
    }
}