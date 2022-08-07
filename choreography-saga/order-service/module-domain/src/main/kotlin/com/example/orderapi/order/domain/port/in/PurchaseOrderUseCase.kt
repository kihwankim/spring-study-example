package com.example.orderapi.order.domain.port.`in`

import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase

interface PurchaseOrderUseCase {
    fun purchaseOrder(order: Order): OrderPurchase
}