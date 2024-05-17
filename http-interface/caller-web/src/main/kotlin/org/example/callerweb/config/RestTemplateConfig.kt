package org.example.callerweb.config

import io.micrometer.observation.ObservationRegistry
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.example.callerweb.client.CalleeClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.support.RestTemplateAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilderFactory
import java.time.Duration


@Configuration
class RestTemplateConfig(
    @Value("\${client.local.url}") private val baseUrl: String,
    private val observerRegistry: ObservationRegistry,
) {

    @Bean
    fun restTemplate(): RestTemplate {
        val connectionManager = PoolingHttpClientConnectionManager().apply {
            maxTotal = 200 // 최대 전체 커넥션 수, default 25
            defaultMaxPerRoute = 200 // 동일 호스트당 최대 커넥션 수, default 5
        }

        // CloseableHttpClient를 생성하고 커넥션 풀을 설정합니다.
        val httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build()

        // HTTP 요청 팩토리를 Apache HttpClient를 사용하여 생성합니다.
        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient).apply {
            setConnectionRequestTimeout(Duration.ofMillis(3000L))
            setConnectTimeout(Duration.ofMillis(3000L))
        }

        // RestTemplate을 생성하고 요청 팩토리를 설정합니다.
        return RestTemplate(requestFactory).apply {
            observationRegistry = observerRegistry
        }
    }

    @Bean
    fun localUriBuilderFactory(): UriBuilderFactory = DefaultUriBuilderFactory(baseUrl)

    @Bean
    fun calleeClient(): CalleeClient {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(RestTemplateAdapter.create(restTemplate()))
            .build()

        return httpServiceProxyFactory.createClient(CalleeClient::class.java)
    }
}