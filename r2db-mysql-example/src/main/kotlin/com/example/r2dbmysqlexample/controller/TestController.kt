package com.example.r2dbmysqlexample.controller

import com.example.r2dbmysqlexample.persistence.respository.TestTlbRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testTlbRepository: TestTlbRepository,
) {
    @GetMapping("/data-get")
    fun findAll() = testTlbRepository.findAll()
}