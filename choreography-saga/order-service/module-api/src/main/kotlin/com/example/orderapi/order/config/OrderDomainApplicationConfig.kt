package com.example.orderapi.order.config

import com.example.orderapi.order.application.OrderService
import com.example.orderapi.order.domain.port.out.OrderPort
import com.example.orderapi.order.domain.port.out.PayOrderPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderDomainApplicationConfig {

    @Bean
    fun orderService(
        orderPort: OrderPort,
        payOrderPort: PayOrderPort
    ): OrderService {
        return OrderService(orderPort, payOrderPort)
    }
}