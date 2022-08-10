package com.example.orderapi.order.domain.dto

data class OrderPurchase(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val orderHashKey: String,
)