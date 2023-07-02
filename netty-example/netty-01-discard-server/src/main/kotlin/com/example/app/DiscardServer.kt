package com.example.app

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

fun main() {
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()

    val b = ServerBootstrap()
    try {
        b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    val p = ch.pipeline()
                    p.addLast(DiscardServerHandler())
                }
            })


        val f = b.bind(8888).sync()

        f.channel().closeFuture().sync()
    } finally {
        workerGroup.shutdownGracefully()
        bossGroup.shutdownGracefully()
    }
}