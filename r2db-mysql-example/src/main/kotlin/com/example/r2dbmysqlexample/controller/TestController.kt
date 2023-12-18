package com.example.r2dbmysqlexample.controller

import com.example.r2dbmysqlexample.config.KLogger
import com.example.r2dbmysqlexample.persistence.entity.TestTlb
import com.example.r2dbmysqlexample.persistence.respository.TestTlbRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testTlbRepository: TestTlbRepository,
) {

    companion object : KLogger()

    @GetMapping("/data-get")
    suspend fun findAll(): List<TestTlb> {
        log.info("${Thread.currentThread()}: message!!")
        return testTlbRepository.findAll().collectList().awaitSingle()
            .also {
                log.info("${Thread.currentThread()}: end before")
                delay(1000L)
                log.info("${Thread.currentThread()}: end")
            }
    }
}