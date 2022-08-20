package com.example.orderapi.order.domain.port.`in`

import com.example.orderapi.order.domain.command.OrderPayEvent

interface PayEventHandleUseCase {
    fun markPaySuccess(orderPayEvent: OrderPayEvent)
}