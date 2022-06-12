package com.example.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmailApplication

fun main(args: Array<String>) {
    runApplication<EmailApplication>(*args)
}
