package com.example.kotlinmultimodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ModuleBatchApplication

fun main(args: Array<String>) {
    runApplication<ModuleBatchApplication>(*args)
}
