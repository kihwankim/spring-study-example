package com.example.springcloudcontract

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CallerApplication

fun main(args: Array<String>) {
    runApplication<CallerApplication>(*args)
}
