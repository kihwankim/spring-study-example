package com.example.orderapi.order.domain.port.out

import com.example.orderapi.order.domain.command.OrderCreateCommand
import com.example.orderapi.order.domain.command.OrderPayEvent
import com.example.orderapi.order.domain.model.Order

interface OrderPort {
    fun purchaceProductByOrder(orderCreateCommand: OrderCreateCommand): Order

    fun markPaySuccess(orderPayEvent: OrderPayEvent)

    fun markFail(orderPayEvent: OrderPayEvent)
}