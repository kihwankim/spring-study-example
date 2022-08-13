package com.example.orderapi.order.domain.port.out

import com.example.orderapi.order.domain.command.OrderCreateCommand
import com.example.orderapi.order.domain.dto.Order
import com.example.orderapi.order.domain.dto.OrderPurchase

interface OrderPort {
    fun purchaceProductByOrder(orderCreateCommand: OrderCreateCommand): Order
}