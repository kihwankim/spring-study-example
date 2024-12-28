package org.example.callerweb.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilderFactory

@Configuration
class ClientConfig(
    @Value("\${client.local.url}") private val baseLocalUrl: String,
) {
    companion object {
        val DEFAULT_OBJECT_MAPPER = jacksonObjectMapper().apply {
            registerModules(JavaTimeModule(), ParameterNamesModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }

    @Bean
    fun localUriBuilderFactory(): UriBuilderFactory = DefaultUriBuilderFactory(baseLocalUrl)
}
