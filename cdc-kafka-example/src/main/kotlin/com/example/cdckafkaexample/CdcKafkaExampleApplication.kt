package com.example.cdckafkaexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CdcKafkaExampleApplication

fun main(args: Array<String>) {
    runApplication<CdcKafkaExampleApplication>(*args)
}
