package org.example.feignclientexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FeignClientExampleApplication

fun main(args: Array<String>) {
    runApplication<FeignClientExampleApplication>(*args)
}
