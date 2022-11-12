package com.example.redispubsub

import com.example.redispubsub.util.TestCallerUtil

data class Message(
    val roomId: Long,
    val chat: String
)

fun main(args: Array<String>) {
    TestCallerUtil.callPost(50, "http://localhost:8080/message")
}