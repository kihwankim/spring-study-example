package com.example.parallel_consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParallelConsumerApplication

fun main(args: Array<String>) {
    runApplication<ParallelConsumerApplication>(*args)
}
