package com.example.kafka.order.application

import com.example.kafka.order.domain.Order
import com.example.kafka.order.domain.port.`in`.OrderReceiptUseCase
import com.example.kafka.order.domain.port.out.ProductValidatePort

class OrderService(
    private val productValidatePort: ProductValidatePort
) : OrderReceiptUseCase {
    override fun orderProduct(order: Order) {
        productValidatePort.verifyProduct(order)
    }
}