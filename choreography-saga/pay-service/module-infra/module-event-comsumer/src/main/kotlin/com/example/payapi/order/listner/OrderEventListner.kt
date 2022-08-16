package com.example.payapi.order.listner

import com.example.payapi.order.entity.OrderPurchase
import com.example.payapi.pay.port.`in`.PayMoneyUseCase
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class OrderEventListner(
    private val payMoneyUseCase: PayMoneyUseCase,
) {

    @EventListener
    fun purchaseEventHandler(orderPurchase: OrderPurchase) {
        print(orderPurchase)
        logger.info("handle order purchase event")
        payMoneyUseCase.payMoney(orderPurchase.toPayCommand())
    }
}