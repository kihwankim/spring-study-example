package com.example.payapi.pay.application

import com.example.common.domain.ActResult
import com.example.common.domain.ErrorResponse
import com.example.payapi.pay.domain.command.PayCommand
import com.example.payapi.pay.port.`in`.PayMoneyUseCase
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import com.example.payapi.pay.port.out.PaymentPort
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class PayService(
    private val paymentPort: PaymentPort,
    private val paymentFailHandlerPort: PaymentFailHandlerPort,
) : PayMoneyUseCase {
    override fun payMoney(payCommand: PayCommand) {
        ActResult { paymentPort.pay(payCommand) }
            .onFailure { handleFailure(it, payCommand) }
    }

    private fun handleFailure(it: ErrorResponse, payCommand: PayCommand) {
        logger.info("pay error")
        paymentFailHandlerPort.sendPaymentRecover(payCommand)
        it.throwError()
    }
}