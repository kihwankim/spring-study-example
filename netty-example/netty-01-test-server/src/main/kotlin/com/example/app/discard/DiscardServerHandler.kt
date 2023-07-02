package com.example.app.discard

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class DiscardServerHandler : SimpleChannelInboundHandler<Any>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Any?) {
        println("do Nothing")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}