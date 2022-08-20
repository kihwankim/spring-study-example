package com.example.orderapi.order.listner

import com.example.orderapi.order.entity.OrderPayFailEvent
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger { }

@Component
class OrderEventListner {
    @EventListener
    fun handleOrderFailEvent(orderPayFailEvent: OrderPayFailEvent) {
        logger.info("handle payment fail about order ${orderPayFailEvent.orderId}")
    }
}