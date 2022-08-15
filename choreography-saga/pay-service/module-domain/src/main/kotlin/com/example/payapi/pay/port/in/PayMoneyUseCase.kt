package com.example.payapi.pay.port.`in`

import com.example.payapi.pay.domain.command.PayCommand

interface PayMoneyUseCase {
    fun payMoney(payCommand: PayCommand)
}