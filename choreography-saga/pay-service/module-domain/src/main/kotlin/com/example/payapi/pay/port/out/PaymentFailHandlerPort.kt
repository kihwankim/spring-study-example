package com.example.payapi.pay.port.out

import com.example.payapi.pay.domain.command.PayCommand

interface PaymentFailHandlerPort {
    fun sendPaymentRecover(payCommand: PayCommand)
}