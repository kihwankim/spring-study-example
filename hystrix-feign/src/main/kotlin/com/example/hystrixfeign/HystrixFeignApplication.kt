package com.example.hystrixfeign

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HystrixFeignApplication

fun main(args: Array<String>) {
    runApplication<HystrixFeignApplication>(*args)
}
