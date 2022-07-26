package com.example.kafka.order.adapter

import com.example.kafka.order.domain.Order
import com.example.kafka.order.domain.port.out.ProductValidatePort
import org.springframework.stereotype.Component

@Component
class ProductAdapter : ProductValidatePort {
    override fun verifyProduct(order: Order) {
    }
}