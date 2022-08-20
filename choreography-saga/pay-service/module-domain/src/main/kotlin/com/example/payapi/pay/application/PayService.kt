package com.example.payapi.pay.application

import com.example.common.domain.ActResult
import com.example.common.domain.ErrorResponse
import com.example.payapi.pay.domain.command.PayCommand
import com.example.payapi.pay.domain.event.OrderPayFailEvent
import com.example.payapi.pay.domain.event.OrderPaySuccessEvent
import com.example.payapi.pay.port.`in`.PayMoneyUseCase
import com.example.payapi.pay.port.out.PaySuccessHandlerPort
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import com.example.payapi.pay.port.out.PaymentPort
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class PayService(
    private val paymentPort: PaymentPort,
    private val paySuccessHandlerPort: PaySuccessHandlerPort,
    private val paymentFailHandlerPort: PaymentFailHandlerPort,
) : PayMoneyUseCase {
    override fun payMoney(payCommand: PayCommand) {
        ActResult { paymentPort.pay(payCommand) }
            .onSuccess { payId -> handlePaySuccess(payId, payCommand) }
            .onFailure { err -> handleFailure(err, payCommand) }
    }

    private fun handlePaySuccess(payId: Long, payCommand: PayCommand) {
        logger.info("pay success $payId, orderId: ${payCommand.orderId}")

        paySuccessHandlerPort.sendOrderPaySucessEvent(
            OrderPaySuccessEvent(
                orderId = payCommand.orderId,
                payId = payId,
                userId = payCommand.userId,
                totalPrice = payCommand.totalPrice
            )
        )
    }

    private fun handleFailure(it: ErrorResponse, payCommand: PayCommand) {
        logger.info("pay error")

        paymentFailHandlerPort.sendOrderPaymentRecover(OrderPayFailEvent(userId = payCommand.userId, orderId = payCommand.orderId, totalPrice = payCommand.totalPrice))
        it.throwError()
    }
}