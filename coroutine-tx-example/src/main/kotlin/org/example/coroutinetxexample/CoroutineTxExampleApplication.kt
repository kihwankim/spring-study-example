package org.example.coroutinetxexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutineTxExampleApplication

fun main(args: Array<String>) {
    runApplication<CoroutineTxExampleApplication>(*args)
}
