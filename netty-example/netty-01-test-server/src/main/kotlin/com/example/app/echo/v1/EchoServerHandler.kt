package com.example.app.echo.v1

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.nio.charset.Charset

class EchoServerHandler : ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val sendMessage = "Hello Netty"
        val messageBuffer = Unpooled.buffer()
        messageBuffer.writeBytes(sendMessage.toByteArray())
        val builder = StringBuilder()
        builder.append(sendMessage)
        println(sendMessage)
        ctx.writeAndFlush(sendMessage)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val readMessage = (msg as ByteBuf).toString(Charset.defaultCharset())

        println("[$readMessage]")
        ctx.writeAndFlush(readMessage)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.close()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}