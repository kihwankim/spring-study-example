package com.example.orderapi.product.config

import com.example.orderapi.product.application.ProductService
import com.example.orderapi.product.domain.port.out.ItemPersistPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ItemDomainApplicationConfig {
    @Bean
    fun itemService(
        itemPersistPort: ItemPersistPort,
    ): ProductService {
        return ProductService(itemPersistPort)
    }
}