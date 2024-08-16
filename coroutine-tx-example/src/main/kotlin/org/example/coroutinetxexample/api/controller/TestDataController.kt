package org.example.coroutinetxexample.api.controller

import org.example.coroutinetxexample.application.service.TestDataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestDataController(
    private val testDataService: TestDataService,
) {
    @GetMapping("/save-loop")
    fun saveTemp() {
        testDataService.saveLoop()
    }
}