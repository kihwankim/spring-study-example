package com.example.kafkastreamstest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaStreamsTestApplication

fun main(args: Array<String>) {
    runApplication<KafkaStreamsTestApplication>(*args)
}
