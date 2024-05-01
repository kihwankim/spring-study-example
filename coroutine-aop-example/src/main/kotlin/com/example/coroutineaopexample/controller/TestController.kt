package com.example.coroutineaopexample.controller

import com.example.coroutineaopexample.service.TestService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController(
    private val testService: TestService,
) {

    @GetMapping("/call-suspend-aop")
    suspend fun callAop(): String {
        return testService.callSuspendService()
    }

    @GetMapping("/call-suspend-flow-aop")
    suspend fun callFlowAop(): Flow<String> {
        return testService.callFlowSuspendService()
    }

    @GetMapping("/call-reactor")
    fun callReactor(): Mono<String> {
        return testService.callReactor()
    }
}