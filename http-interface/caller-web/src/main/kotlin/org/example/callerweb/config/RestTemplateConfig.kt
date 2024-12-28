package org.example.callerweb.config

import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.example.callerweb.client.CalleeRestTemplate
import org.springframework.boot.actuate.metrics.web.client.ObservationRestTemplateCustomizer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.support.RestTemplateAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.time.Duration

@Configuration
class RestTemplateConfig(
    private val restTemplateBuilder: RestTemplateBuilder,
    private val observationRestTemplateCustomizer: ObservationRestTemplateCustomizer,
) {

    @Bean
    fun restTemplate(): RestTemplate {
        return restTemplateBuilder
            .customizers(observationRestTemplateCustomizer)
            .messageConverters(
                StringHttpMessageConverter(Charsets.UTF_8),
                MappingJackson2HttpMessageConverter(ClientConfig.DEFAULT_OBJECT_MAPPER),
            )
            .requestFactory { -> createHttpRequestFactory() }
            .build()
    }

    private fun createHttpRequestFactory(): ClientHttpRequestFactory {
        val connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setMaxConnTotal(500) // 최대 전체 커넥션 수, default 25
            .setMaxConnPerRoute(200) // 동일 호스트당 최대 커넥션 수, default 5
            .build()

        // HTTP 요청 팩토리를 Apache HttpClient를 사용하여 생성합니다.
        // CloseableHttpClient를 생성하고 커넥션 풀을 설정합니다.
        val httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build()

        return HttpComponentsClientHttpRequestFactory(httpClient).apply {
            setConnectionRequestTimeout(Duration.ofMillis(3_000L))
            setConnectTimeout(Duration.ofMillis(3_000L))
        }
    }

    @Bean
    fun calleeRestTemplate(): CalleeRestTemplate {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(RestTemplateAdapter.create(restTemplate()))
            .build()

        return httpServiceProxyFactory.createClient(CalleeRestTemplate::class.java)
    }
}