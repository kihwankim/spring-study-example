package com.example.orderapi.order.listner

import com.example.orderapi.order.domain.port.`in`.PayEventHandleUseCase
import com.example.orderapi.order.entity.OrderPayFailEvent
import com.example.orderapi.order.entity.OrderPaySuccessEvent
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger { }

@Component
class OrderEventListner(
    private val payEventHandleUseCase: PayEventHandleUseCase,
) {
    @EventListener
    fun handleOrderPayFailEvent(orderPayFailEvent: OrderPayFailEvent) {
        logger.info("handle payment fail about order ${orderPayFailEvent.orderId}")
        payEventHandleUseCase.markFail(orderPayFailEvent.toOrderPayEvent())
    }

    @EventListener
    fun handleOrderPaySucessEvent(orderPaySuccessEvent: OrderPaySuccessEvent) {
        logger.info("handle payment success about order ${orderPaySuccessEvent.orderId}")
        payEventHandleUseCase.markPaySuccess(orderPaySuccessEvent.toOrderPayEvent())
    }
}