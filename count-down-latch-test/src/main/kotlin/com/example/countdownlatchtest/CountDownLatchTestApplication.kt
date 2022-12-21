package com.example.countdownlatchtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CountDownLatchTestApplication

fun main(args: Array<String>) {
    runApplication<CountDownLatchTestApplication>(*args)
}
