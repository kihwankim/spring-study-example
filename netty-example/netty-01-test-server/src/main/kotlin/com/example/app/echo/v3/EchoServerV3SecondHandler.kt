package com.example.app.echo.v3

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class EchoServerV3SecondHandler : ChannelInboundHandlerAdapter() {

//    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
//        val readMessage = msg as ByteBuf
//        println("channelRead Sec: ${readMessage.toString(Charset.defaultCharset())}")
//        ctx.write(msg)
//    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        println("channelReadComplete Sec 발생")
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}