package org.example.callerweb.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilderFactory

@Configuration
class ClientConfig(
    @Value("\${client.local.url}") private val baseLocalUrl: String,
) {
    @Bean
    fun localUriBuilderFactory(): UriBuilderFactory = DefaultUriBuilderFactory(baseLocalUrl)
}
