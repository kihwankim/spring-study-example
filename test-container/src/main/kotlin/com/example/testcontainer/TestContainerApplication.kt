package com.example.testcontainer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestContainerApplication

fun main(args: Array<String>) {
    runApplication<TestContainerApplication>(*args)
}
