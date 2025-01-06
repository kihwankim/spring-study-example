package com.example.mongobatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoBatchApplication

fun main(args: Array<String>) {
    runApplication<MongoBatchApplication>(*args)
}
