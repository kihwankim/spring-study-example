package com.example.orderapi.order.domain.dto

import java.math.BigDecimal

data class Order(
    val orderId: Long, // order 식별 id
    val userId: Long, // 구메자 id
    val orderStatus: OrderStatus, // 현재 order 상태
    val totalPrice: BigDecimal,
    val nowEventKey: String,
    val version: Long,
    val orderProductItems: List<OrderProductItem>, // 구메할 데이터
)
