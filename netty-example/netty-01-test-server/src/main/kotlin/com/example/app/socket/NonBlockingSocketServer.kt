package com.example.app.socket

import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class NonBlockingSocketServer(
    // non blocking socket으로 부터 읽은 데이터는 바로 write 불가능
    private val keepDataTrack: MutableMap<SocketChannel, MutableList<ByteArray>> = HashMap(),
    private val buffer: ByteBuffer = ByteBuffer.allocate(2 * 1024),
) {

    fun startEchoServer() {
        val openedSelector: Selector = Selector.open() // channel 의 변경 사항 확인
        val serverSocketChannel: ServerSocketChannel = ServerSocketChannel.open()
        try {
            openedSelector.use { selector ->
                serverSocketChannel.use { serverSocketChannel ->
                    if (serverSocketChannel.isOpen && selector.isOpen) {
                        serverSocketChannel.configureBlocking(false)
                        serverSocketChannel.bind(InetSocketAddress(8888)) // port binding

                        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT) // selector가 감지 할 이벤트는 `OP_ACCEPT` 이라는 것을 알려줌
                        println("접속 대기중")

                        while (true) {
                            selector.select() // 변경 사항 감지
                            val keys = selector.selectedKeys().iterator() // 감지된 이벤트가 존재하는 channel들 key 조회

                            while (keys.hasNext()) {
                                val key = keys.next()
                                keys.remove() // 이미 조회한 event가 다음 loop/다른 thread에서 중복으로 조회되는 것을 방지하기 위해서 제거

                                if (!key.isValid) {
                                    continue
                                }

                                if (key.isAcceptable) {
                                    this.acceptOP(key, selector)
                                } else if (key.isReadable) {
                                    this.readOP(key)
                                } else if (key.isWritable) {
                                    this.writeOP(key)
                                }
                            }
                        }
                    } else {
                        println("서버 소켓을 생성하지 못했습니다.")
                    }
                }
            }
        } catch (ex: IOException) {
            println(ex)
        }
    }

    private fun acceptOP(key: SelectionKey, selector: Selector) {
        val serverChannel = key.channel() as ServerSocketChannel
        val socketChannel = serverChannel.accept()
        socketChannel.configureBlocking(false)

        keepDataTrack[socketChannel] = ArrayList()
        socketChannel.register(selector, SelectionKey.OP_READ)
    }

    private fun readOP(key: SelectionKey) {
        try {
            val socketChannel = key.channel() as SocketChannel
            buffer.clear()
            var numRead = -1
            try {
                numRead = socketChannel.read(buffer)
            } catch (e: IOException) {
                println("데이터 읽기 에러!")
            }

            if (numRead == -1) {
                keepDataTrack.remove(socketChannel)
                println("클라이언트 연결 종료: ${socketChannel.remoteAddress}")
                socketChannel.close()
                key.cancel()
                return
            }
            val data = ByteArray(numRead)
            System.arraycopy(buffer.array(), 0, data, 0, numRead)
            println("${java.lang.String(data, "UTF-8")} from ${socketChannel.remoteAddress}")

            doEchoJob(key, data)
        } catch (ex: IOException) {
            println(ex)
        }
    }

    private fun writeOP(key: SelectionKey) {
        val socketChannel = key.channel() as SocketChannel

        val channelData = keepDataTrack[socketChannel]!!
        val its = channelData.iterator()
        while (its.hasNext()) {
            val it = its.next()
            its.remove()
            socketChannel.write(ByteBuffer.wrap(it))
        }

        key.interestOps(SelectionKey.OP_READ)
    }

    private fun doEchoJob(key: SelectionKey, data: ByteArray) {
        val socketChannel = key.channel() as SocketChannel
        val channelData = keepDataTrack[socketChannel]
        channelData?.add(data)

        key.interestOps(SelectionKey.OP_WRITE)
    }
}

fun main() {
    val main = NonBlockingSocketServer()
    main.startEchoServer()
}