package com.example.payapi.pay.port.out

import com.example.payapi.pay.domain.command.PayFailHandleEvent

interface PaymentFailHandlerPort {
    fun sendOrderPaymentRecover(payFailHandleEvent: PayFailHandleEvent)
}