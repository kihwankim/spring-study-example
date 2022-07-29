package com.example.kafka.order.adapter.message.dto

data class ConsumerMessageDto(
    val orderId: Long,
    val orderName: String,
    val numberOfProduct: Int
)