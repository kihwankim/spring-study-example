package com.example.orderapi.order.adapter.presentation.dto

data class OrderPurchaseRequest(
    val userId: Long,
    val productId: Long
)