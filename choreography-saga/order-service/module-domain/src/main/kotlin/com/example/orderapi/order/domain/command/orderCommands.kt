package com.example.orderapi.order.domain.command

data class OrderCreateCommand(
    val userId: Long,
    val purchaseProducts: List<PurchaseProduct>
)


data class PurchaseProduct(
    val productId: Long,
    val productQuantity: Int,
)