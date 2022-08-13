package com.example.orderapi.order.domain.dto

data class OrderProductItem(
    val product: Product,
    val quantity: Int,
)
