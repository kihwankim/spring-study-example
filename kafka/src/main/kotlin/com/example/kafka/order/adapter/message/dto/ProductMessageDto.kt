package com.example.kafka.order.adapter.message.dto

data class ProductMessageDto(
    val orderId: Long,
    val orderName: String,
    val numberOfProduct: Int
)
