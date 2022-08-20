package com.example.payapi.pay.port.out

import com.example.payapi.pay.domain.event.OrderPayFailEvent

interface PaymentFailHandlerPort {
    fun sendOrderPaymentRecover(orderPayFailEvent: OrderPayFailEvent)
}