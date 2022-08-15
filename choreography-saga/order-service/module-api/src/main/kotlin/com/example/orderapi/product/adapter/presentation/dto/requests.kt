package com.example.orderapi.product.adapter.presentation.dto

import com.example.orderapi.product.domain.command.ProductCreateCommand
import java.math.BigDecimal


data class ProductCreateRequest(
    val userId: Long,
    val inventory: Int,
    val price: BigDecimal,
    val productName: String,
    val description: String,
) {
    fun toCommand(): ProductCreateCommand = ProductCreateCommand(
        userId = this.userId,
        inventory = this.inventory,
        price = this.price,
        productName = this.productName,
        description = this.description
    )
}