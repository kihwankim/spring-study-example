package com.example.app.echo.http_dec

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.*
import io.netty.util.AsciiString

class HttpCodecHelloWordServer : ChannelInboundHandlerAdapter() {
    companion object {
        private val CONTENT: ByteArray = "Hello World".toByteArray()
        private val CONTENT_TYPE = AsciiString("Content-Type")
        private val CONTENT_LENGTH = AsciiString("Content-Lenght")
        private val CONNECTION = AsciiString("Connection")
        private val KEEP_ALIVE = AsciiString("keep-alive")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
        ctx.close()
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is HttpRequest) {
            if (HttpHeaders.is100ContinueExpected(msg as HttpMessage)) {
                ctx.write(DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE))
            }
            val keepAlive = HttpHeaders.isKeepAlive(msg)
            val response = DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(CONTENT)
            )
            response.headers().set(CONTENT_TYPE, "text/plain")
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes())

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE)
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE)
                ctx.write(response)
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}