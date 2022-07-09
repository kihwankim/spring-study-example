package com.example.hystrixfeign.api

import com.example.hystrixfeign.adapater.CallApiClient
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EndPointController(
    private val callApiClient: CallApiClient,
    private val circuitBreakerFactory: CircuitBreakerFactory<*, *>
) {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        callApiClient.calledData()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/timeout")
    fun callTimeOutServer(): ResponseEntity<String> {
        return ResponseEntity.ok(callApiClient.calledTimeOut())
    }

    @GetMapping("/fail")
    fun callFailServer(): ResponseEntity<String> {
        return ResponseEntity.ok(
            circuitBreakerFactory.create("break").run({ callApiClient.callFiled() }, { "recover" })
        )
    }
}