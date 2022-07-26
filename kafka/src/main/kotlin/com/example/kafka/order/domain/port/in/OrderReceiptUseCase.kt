package com.example.kafka.order.domain.port.`in`

import com.example.kafka.order.domain.Order

interface OrderReceiptUseCase {
    fun orderProduct(order: Order)
}