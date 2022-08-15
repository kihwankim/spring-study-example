package com.example.payapi.pay.config

import com.example.payapi.pay.application.PayService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PayAppConfig {
    @Bean
    fun payService(): PayService {
        return PayService()
    }
}