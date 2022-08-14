package com.example.orderapi.order.adapter.presentation.dto

import com.example.orderapi.order.domain.model.OrderStatus


data class OrderPurchageResponse(
    val purchageId: Long,
    val userId: Long,
    val productId: Long,
    val price: Double,
    val orderStatus: OrderStatus
)