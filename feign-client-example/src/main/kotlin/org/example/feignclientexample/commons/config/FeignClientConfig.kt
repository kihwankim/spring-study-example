package org.example.feignclientexample.commons.config

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Logger
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException


@Configuration
class FeignClientConfig(
    private val objectMapper: ObjectMapper,
) {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return GlobalFeignErrorDecoder(objectMapper)
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

    class GlobalFeignErrorDecoder(
        private val objectMapper: ObjectMapper,
    ) : ErrorDecoder {
        override fun decode(str: String?, response: Response?): Exception {
            return try {
                response?.body()?.asInputStream()?.use { responseBody ->
                    log.info("error body: $responseBody")
                    IllegalArgumentException()
                } ?: RuntimeException()
            } catch (e: IOException) {
                IllegalArgumentException()
            }
        }

    }
}