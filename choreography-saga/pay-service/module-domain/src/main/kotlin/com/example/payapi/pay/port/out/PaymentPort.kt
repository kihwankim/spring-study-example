package com.example.payapi.pay.port.out

import com.example.common.domain.ActResult
import com.example.payapi.pay.domain.command.PayCommand

interface PaymentPort {
    fun pay(payCommand: PayCommand): ActResult<Long>
}