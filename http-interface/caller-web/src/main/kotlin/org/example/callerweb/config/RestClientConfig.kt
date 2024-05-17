package org.example.callerweb.config

import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.example.callerweb.client.CalleeRestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.time.Duration

@Configuration
class RestClientConfig(
    @Value("\${client.local.url}") private val baseUrl: String,
    private val observationRestClientCustomizer: RestClientCustomizer,
) {

    @Bean
    fun restClient(): RestClient {
        return RestClient.builder().apply {
            observationRestClientCustomizer.customize(it)
        }.defaultHeaders { httpHeaders ->
            httpHeaders.acceptCharset = listOf(Charsets.UTF_8)
        }.requestFactory(createHttpRequestFactory())
            .build()
    }

    private fun createHttpRequestFactory(): ClientHttpRequestFactory {
        val connectionManager = PoolingHttpClientConnectionManager().apply {
            maxTotal = 200 // 최대 전체 커넥션 수, default 25
            defaultMaxPerRoute = 200 // 동일 호스트당 최대 커넥션 수, default 5
        }

        // HTTP 요청 팩토리를 Apache HttpClient를 사용하여 생성합니다.
        // CloseableHttpClient를 생성하고 커넥션 풀을 설정합니다.
        val httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build()

        return HttpComponentsClientHttpRequestFactory(httpClient).apply {
            setConnectionRequestTimeout(Duration.ofMillis(3000L))
            setConnectTimeout(Duration.ofMillis(3000L))
        }
    }

    @Bean
    fun CalleeRestClient(): CalleeRestClient {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient()))
            .build()

        return httpServiceProxyFactory.createClient(CalleeRestClient::class.java)
    }
}