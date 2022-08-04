package com.example.payapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PayServiceApplication

fun main(args: Array<String>) {
    runApplication<PayServiceApplication>(*args)
}
