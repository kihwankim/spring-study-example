package com.example.orderapi.order.domain.model

import java.math.BigDecimal

data class OrderPurchase(
    val orderId: Long,
    val totalPrice: BigDecimal,
    val orderStatus: OrderStatus,
    val orderHashKey: String,
    val orderProductItems: List<OrderProductItem>,
)