package com.example.orderapi.order.adapter.presentation.dto

import com.example.orderapi.order.domain.command.OrderCreateCommand
import com.example.orderapi.order.domain.command.PurchaseProduct

data class OrderPurchaseRequest(
    val userId: Long,
    val products: List<ProductRequest>,
) {
    fun toOrderCommand(): OrderCreateCommand {
        return OrderCreateCommand(userId, products.map { PurchaseProduct(it.productId, it.productQuantity) })
    }
}

data class ProductRequest(
    val productId: Long,
    val productQuantity: Int,
)