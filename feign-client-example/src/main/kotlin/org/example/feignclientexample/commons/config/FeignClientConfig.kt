package org.example.feignclientexample.commons.config

import feign.Logger
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.example.feignclientexample.commons.enums.ErrorType
import org.example.feignclientexample.commons.exceptions.AppException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException


@Configuration
class FeignClientConfig {
    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return GlobalFeignErrorDecoder()
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

    class GlobalFeignErrorDecoder : ErrorDecoder {
        override fun decode(str: String?, response: Response?): Exception {
            return try {
                response?.body()?.asInputStream()?.use { responseBody ->
                    log.info("error body: $responseBody")
                    AppException(errorType = ErrorType.BAD_GATEWAY)
                } ?: AppException(errorType = ErrorType.BAD_GATEWAY)
            } catch (e: IOException) {
                AppException(errorType = ErrorType.BAD_GATEWAY)
            }
        }

    }
}