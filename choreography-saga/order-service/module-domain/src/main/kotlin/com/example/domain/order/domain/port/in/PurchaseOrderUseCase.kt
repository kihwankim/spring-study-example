package com.example.domain.order.domain.port.`in`

import com.example.domain.order.domain.dto.Order
import com.example.domain.order.domain.dto.OrderPurchase

interface PurchaseOrderUseCase {
    fun purchaseOrder(order: Order): OrderPurchase
}