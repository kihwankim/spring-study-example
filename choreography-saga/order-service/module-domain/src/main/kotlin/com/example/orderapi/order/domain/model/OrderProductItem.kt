package com.example.orderapi.order.domain.model

import com.example.orderapi.product.domain.model.Product

data class OrderProductItem(
    val product: Product,
    val quantity: Int,
)
