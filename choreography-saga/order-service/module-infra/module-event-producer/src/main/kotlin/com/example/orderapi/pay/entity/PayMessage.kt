package com.example.orderapi.pay.entity

import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.dto.OrderStatus

internal data class PayMessage(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val orderHashKey: String,
) {
    companion object {
        fun from(purchase: OrderPurchase): PayMessage {
            return PayMessage(
                orderId = purchase.orderId,
                orderStatus = purchase.orderStatus,
                orderHashKey = purchase.orderHashKey
            )
        }
    }
}
