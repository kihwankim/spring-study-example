package com.example.app.echo.http_dec

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.ssl.SslContext

class HttpHelloWorldServerIntializer(
    private val sslCtx: SslContext?,
) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val p = ch.pipeline()
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()))
        }

        p.addLast(HttpServerCodec())
        p.addLast(HttpCodecHelloWordServer())
    }
}