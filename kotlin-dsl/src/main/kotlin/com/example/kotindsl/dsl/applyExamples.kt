package com.example.kotindsl.dsl

data class TestObj(
    var id: Long = 0L,
    var name: String = ""
)


fun main() {
    TestObj().apply {
        id = 1
        name = "test name"
    }
}