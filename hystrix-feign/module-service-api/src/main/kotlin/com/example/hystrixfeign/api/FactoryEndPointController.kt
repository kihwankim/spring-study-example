package com.example.hystrixfeign.api

import com.example.hystrixfeign.application.CirtcuitFactoryWayService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/factory")
class FactoryEndPointController(
    private val circuitFactoryWayService: CirtcuitFactoryWayService
) {
    @GetMapping("/call")
    fun callNextServer(): ResponseEntity<Unit> {
        circuitFactoryWayService.callNoraml()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/timeout")
    fun callTimeOutServer(): ResponseEntity<String> {
        return ResponseEntity.ok(circuitFactoryWayService.callTimeout())
    }

    @GetMapping("/fail")
    fun callFailServer(): ResponseEntity<String> {
        return ResponseEntity.ok(circuitFactoryWayService.callNotFound())
    }
}