package com.example.uuididexample.test.controller

import com.example.uuididexample.test.service.TestUUIdService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestUUIdController(
    private val testUUIdService: TestUUIdService,
) {

    @GetMapping("/save-test")
    fun save() = testUUIdService.saveData()
}