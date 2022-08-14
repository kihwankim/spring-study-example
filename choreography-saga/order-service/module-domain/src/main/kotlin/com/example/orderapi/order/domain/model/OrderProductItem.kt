package com.example.orderapi.order.domain.model

data class OrderProductItem(
    val product: Product,
    val quantity: Int,
)
