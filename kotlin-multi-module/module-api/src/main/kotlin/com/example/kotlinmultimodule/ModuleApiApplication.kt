package com.example.kotlinmultimodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ModuleApiApplication

fun main(args: Array<String>) {
	runApplication<ModuleApiApplication>(*args)
}
