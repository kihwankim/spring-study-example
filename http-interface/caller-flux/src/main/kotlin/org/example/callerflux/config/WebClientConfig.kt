package org.example.callerflux.config

import io.netty.channel.ChannelOption
import io.netty.handler.logging.LogLevel
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.example.callerflux.client.CalleeClient
import org.springframework.boot.actuate.metrics.web.reactive.client.ObservationWebClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.netty.tcp.DefaultSslContextSpec
import reactor.netty.transport.logging.AdvancedByteBufFormat
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class WebClientConfig(
    private val observationWebClientCustomizer: ObservationWebClientCustomizer,
) {

    companion object {
        private const val DEFAULT_BUF_SZIE = 10 * (1024 * 1024) // 10MB
        private const val DEFAULT_TIME_OUT_MICRO_SEC = 30_000L
    }

    @Bean
    fun calleeWebClient(): WebClient = createWebClient("callee", "http://localhost:9999")

    @Bean
    fun calleeClient(): CalleeClient {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(calleeWebClient()))
            .build()

        return httpServiceProxyFactory.createClient(CalleeClient::class.java)
    }

    private fun createWebClient(
        name: String,
        baseUrl: String,
        maxConnection: Int = 200,
        maxLifeTime: Duration = Duration.ofSeconds(8L),
        maxIdleTime: Duration = Duration.ofSeconds(8L),
        sslConnectTimeoutMSec: Long = 2500L,
        connectTimeoutMSec: Int = 2500,
        readTimeoutMSec: Long = DEFAULT_TIME_OUT_MICRO_SEC,
        writeTimeoutMSec: Long = DEFAULT_TIME_OUT_MICRO_SEC,
        maxBufferSize: Int = DEFAULT_BUF_SZIE,
        pendingAcquireMaxCount: Int = 1_000,
        pendingAcqTimeoutMSec: Long = 2_000, // 2sec
        evictInBackgroundSec: Long = 0L,
        isFifoLeasingStrategies: Boolean = true, // default fifo
    ): WebClient = WebClient.builder().apply {
        observationWebClientCustomizer.customize(it)
    }.baseUrl(baseUrl)
        .defaultHeaders { httpHeaders -> httpHeaders.acceptCharset = listOf(Charsets.UTF_8) }
        .exchangeStrategies(
            ExchangeStrategies.builder().codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(maxBufferSize)
            }.build(),
        )
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create(
                    ConnectionProvider.builder(name)
                        .maxConnections(maxConnection)
                        .maxLifeTime(maxLifeTime) // 살아 있는 최대 시간
                        .maxIdleTime(maxIdleTime) // 유후 상태 유지 시간
                        .pendingAcquireMaxCount(pendingAcquireMaxCount) // pending 대기자 수
                        .pendingAcquireTimeout(Duration.ofMillis(pendingAcqTimeoutMSec)) // pending 최대 대기 시간
                        .evictInBackground(Duration.ofSeconds(evictInBackgroundSec)) // 비활 성화 제거 시간 간격
                        .metrics(true)
                        .also { if (isFifoLeasingStrategies) it.fifo() else it.lifo() }
                        .build(),
                ).disableRetry(true).option(
                    ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMSec,
                ).doOnConnected { con ->
                    con.addHandlerLast(ReadTimeoutHandler(readTimeoutMSec, TimeUnit.MILLISECONDS))
                    con.addHandlerLast(WriteTimeoutHandler(writeTimeoutMSec, TimeUnit.MILLISECONDS))
                }.secure { spec ->
                    spec.sslContext(DefaultSslContextSpec.forClient())
                        .handshakeTimeout(Duration.ofMillis(sslConnectTimeoutMSec))
                }.wiretap(
                    HttpClient::class.java.name,
                    LogLevel.DEBUG,
                    AdvancedByteBufFormat.TEXTUAL,
                ).also {
                    it.warmup().block()
                },
            ),
        ).build()

}