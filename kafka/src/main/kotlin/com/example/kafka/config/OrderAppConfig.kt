package com.example.kafka.config

import com.example.kafka.order.application.OrderService
import com.example.kafka.order.domain.port.out.ProductValidatePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderAppConfig(
    private val productValidatePort: ProductValidatePort
) {
    @Bean
    fun orderService(): OrderService {
        return OrderService(productValidatePort)
    }
}