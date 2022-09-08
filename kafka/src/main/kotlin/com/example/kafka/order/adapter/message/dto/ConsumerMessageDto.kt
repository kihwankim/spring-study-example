package com.example.kafka.order.adapter.message.dto

import com.example.kafka.order.domain.Order

data class ConsumerMessageDto(
    val orderId: Long,
    val orderName: String,
    val numberOfProduct: Int
) {
    fun toDomainModel(): Order {
        return Order(
            orderId = orderId,
            orderName = orderName,
            count = numberOfProduct
        )
    }
}