package com.example.kafka.order.domain.port.out

import com.example.kafka.order.domain.Order


interface ProductValidatePort {
    fun verifyProduct(order: Order)
}