package com.example.app.echo.v3

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.nio.charset.Charset

class EchoServerV3FirstHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val readMessage = msg as ByteBuf
        println("channelRead: ${readMessage.toString(Charset.defaultCharset())}")
        ctx.write(msg)
    }

//    override fun channelReadComplete(ctx: ChannelHandlerContext) {
//        println("channelReadComplete First 발생")
//        ctx.flush()
//    }

//    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
//        cause.printStackTrace()
//        ctx.close()
//    }
}