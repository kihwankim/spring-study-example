package com.example.springrediscacheuidsetting.controller

import com.example.springrediscacheuidsetting.service.TestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testService: TestService,
) {

    @GetMapping("/test/{id}")
    suspend fun call(@PathVariable("id") id: Long) = withContext(Dispatchers.IO) { testService.callValue(id) }

    @GetMapping("/test")
    suspend fun callAll() = withContext(Dispatchers.IO) { testService.callValues() }

    @GetMapping("/mannual/save/{name}")
    suspend fun save(@PathVariable name: String) {
        withContext(Dispatchers.IO) { testService.saveMannual(name) }
    }

    @GetMapping("/mannual/get/{name}")
    suspend fun findVar(@PathVariable name: String): String? {
        return withContext(Dispatchers.IO) {
            testService.findMannual(name)
        }
    }
}