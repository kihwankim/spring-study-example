package com.example.kafka.order.domain

data class Order(
    val orderId: Long,
    val orderName: String,
    val count: Int
)