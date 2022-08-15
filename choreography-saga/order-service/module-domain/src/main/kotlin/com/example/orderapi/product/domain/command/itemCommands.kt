package com.example.orderapi.product.domain.command

import java.math.BigDecimal

data class ProductCreateCommand(
    val userId: Long,
    val inventory: Int,
    val price: BigDecimal,
    val productName: String,
    val description: String,
)