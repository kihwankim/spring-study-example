package com.example.orderapi.order.domain.command

import com.example.orderapi.order.domain.model.OrderStatus

data class OrderCreateCommand(
    val userId: Long,
    val purchaseProducts: List<PurchaseProduct>
)


data class PurchaseProduct(
    val productId: Long,
    val productQuantity: Int,
)


data class OrderPayEvent(
    val orderId: Long,
    val orderStatus: OrderStatus,
)