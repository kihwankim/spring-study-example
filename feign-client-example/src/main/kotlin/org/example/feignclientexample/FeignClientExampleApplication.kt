package org.example.feignclientexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class FeignClientExampleApplication

fun main(args: Array<String>) {
    runApplication<FeignClientExampleApplication>(*args)
}
