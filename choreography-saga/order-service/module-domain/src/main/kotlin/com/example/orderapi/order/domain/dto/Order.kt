package com.example.orderapi.order.domain.dto

data class Order(
    val orderId: Long,
    val userId: Long,
    val products: List<Product>,
)
