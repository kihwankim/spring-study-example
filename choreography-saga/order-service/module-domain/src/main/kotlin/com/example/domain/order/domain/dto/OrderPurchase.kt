package com.example.domain.order.domain.dto

data class OrderPurchase(
    val orderId: Long,
    val orderStatus: OrderStatus,
)