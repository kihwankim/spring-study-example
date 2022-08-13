package com.example.orderapi.order.domain.port.`in`

import com.example.orderapi.order.domain.command.OrderCreateCommand

interface PurchaseOrderUseCase {
    fun purchaseOrder(orderCreateCommand: OrderCreateCommand)
}