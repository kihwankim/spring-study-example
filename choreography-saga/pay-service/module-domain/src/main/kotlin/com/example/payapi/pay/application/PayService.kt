package com.example.payapi.pay.application

import com.example.payapi.pay.domain.command.PayCommand
import com.example.payapi.pay.port.`in`.PayMoneyUseCase
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class PayService : PayMoneyUseCase {
    override fun payMoney(payCommand: PayCommand) {
        logger.info("pay command call!!")
    }
}