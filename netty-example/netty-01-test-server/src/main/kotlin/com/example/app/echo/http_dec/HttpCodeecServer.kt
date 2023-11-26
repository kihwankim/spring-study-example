package com.example.app.echo.http_dec

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

private val SSL = System.getProperty("ssl") != null
private val PORT = Integer.parseInt(System.getProperty("port", if (SSL) "8843" else "8888"))
fun main() {
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()

    try {
        val b = ServerBootstrap()
        b.option(ChannelOption.SO_BACKLOG, 1024)
        b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel::class.java)
            .handler(LoggingHandler(LogLevel.INFO))
            .childHandler(HttpHelloWorldServerIntializer(null))

        val ch = b.bind(PORT).sync().channel()
        ch.closeFuture().sync()
    } finally {
        workerGroup.shutdownGracefully()
        bossGroup.shutdownGracefully()
    }
}