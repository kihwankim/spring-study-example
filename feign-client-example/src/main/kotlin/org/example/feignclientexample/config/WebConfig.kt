package org.example.feignclientexample.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.feignclientexample.constants.ObjectMapperConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class WebConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = ObjectMapperConstants.DEFAULT_OBJECT_MAPPER
}