package org.example.flowconcurrencyexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlowConcurrencyExampleApplication

fun main(args: Array<String>) {
    runApplication<FlowConcurrencyExampleApplication>(*args)
}
