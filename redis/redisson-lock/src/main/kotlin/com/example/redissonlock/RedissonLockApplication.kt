package com.example.redissonlock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedissonLockApplication

fun main(args: Array<String>) {
    runApplication<RedissonLockApplication>(*args)
}
