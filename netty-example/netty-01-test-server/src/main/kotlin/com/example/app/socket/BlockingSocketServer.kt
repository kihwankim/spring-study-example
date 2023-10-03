package com.example.app.socket

import java.io.IOException
import java.net.ServerSocket

class BlockingSocketServer {
    fun run() {
        val server = ServerSocket(8888)
        println("접속 대기중")

        while (true) {
            val socket = server.accept()
            println("thread: ${Thread.currentThread()}, client 연결됨")

            val out = socket.getOutputStream()
            val `in` = socket.getInputStream()

            while (true) {
                try {
                    val request = `in`.read()
                    println("thread: ${Thread.currentThread()}, request: ${request.toChar()}")
                    out.write(request)
                } catch (e: IOException) {
                    break
                }
            }
        }
    }
}

fun main() {
    val server = BlockingSocketServer()
    server.run()
}