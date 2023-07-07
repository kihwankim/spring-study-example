package com.example.app.echo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

fun main() {
    val group = NioEventLoopGroup()
    try {
        val b = Bootstrap()
        b.group(group)
            .channel(NioSocketChannel::class.java)
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    val p = ch.pipeline()
                    p.addLast(EchoClientHandler())
                }
            })
        val f = b.connect("localhost", 8888).sync()
        f.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}
