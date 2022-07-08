package com.example.hystrixfeign

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestApiMain

fun main(args: Array<String>) {
    runApplication<TestApiMain>(*args)
}
