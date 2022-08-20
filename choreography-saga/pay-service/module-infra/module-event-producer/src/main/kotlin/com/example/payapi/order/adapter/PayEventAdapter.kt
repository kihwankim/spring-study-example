package com.example.payapi.order.adapter

import com.example.common.domain.objectMapper
import com.example.payapi.order.entity.OrderRawEvent
import com.example.payapi.pay.domain.command.PayFailHandleEvent
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger { }

@Component
internal class PayEventAdapter(
    private val kafkaProducerTemplate: KafkaTemplate<String, String>,
) : PaymentFailHandlerPort {
    companion object {
        private const val ORDER_EVENT_TOPIC = "order-event-topic"
    }

    override fun sendOrderPaymentRecover(payFailHandleEvent: PayFailHandleEvent) {
        logger.info("call recover handler order Id: {}, pay topic: {}", payFailHandleEvent.orderId, ORDER_EVENT_TOPIC)
        val orderFailCommand = OrderRawEvent.from(payFailHandleEvent)

        kafkaProducerTemplate.send(ORDER_EVENT_TOPIC, objectMapper.writeValueAsString(orderFailCommand))
    }
}
