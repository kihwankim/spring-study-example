package com.example.hystrixfeign

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceApiMain

fun main(args: Array<String>) {
    runApplication<ServiceApiMain>(*args)
}
