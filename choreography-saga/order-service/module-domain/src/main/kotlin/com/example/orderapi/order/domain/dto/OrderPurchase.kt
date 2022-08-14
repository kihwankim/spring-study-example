package com.example.orderapi.order.domain.dto

import java.math.BigDecimal

data class OrderPurchase(
    val orderId: Long,
    val totalPrice: BigDecimal,
    val orderStatus: OrderStatus,
    val orderHashKey: String,
    val version: Long,
    val orderProductItems: List<OrderProductItem>,
)