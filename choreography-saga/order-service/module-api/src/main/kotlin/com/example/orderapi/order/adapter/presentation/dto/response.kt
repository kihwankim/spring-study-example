package com.example.orderapi.order.adapter.presentation.dto

import com.example.orderapi.order.domain.dto.OrderStatus


data class OrderPurchageResponse(
    val purchageId: Long,
    val userId: Long,
    val productId: Long,
    val price: Double,
    val orderStatus: OrderStatus
)