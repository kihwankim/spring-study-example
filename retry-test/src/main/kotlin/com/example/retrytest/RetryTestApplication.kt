package com.example.retrytest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RetryTestApplication

fun main(args: Array<String>) {
    runApplication<RetryTestApplication>(*args)
}
