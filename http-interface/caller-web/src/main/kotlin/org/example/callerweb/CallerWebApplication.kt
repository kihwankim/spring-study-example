package org.example.callerweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CallerWebApplication

fun main(args: Array<String>) {
    runApplication<CallerWebApplication>(*args)
}
