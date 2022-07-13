package com.example.hystrixfeign

import com.example.hystrixfeign.test.TestCallerUtil

fun main(args: Array<String>) {
    TestCallerUtil.call(10, "http://localhost:8080/annotation/rate-limit")
}