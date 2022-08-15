package com.example.payapi.order.domain.model

data class OrderProductItem(
    val product: Product,
    val quantity: Int,
)
