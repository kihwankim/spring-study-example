package com.example.payapi.order.adapter

import com.example.common.domain.objectMapper
import com.example.payapi.order.entity.OrderRawEvent
import com.example.payapi.pay.domain.event.OrderPayFailEvent
import com.example.payapi.pay.domain.event.OrderPaySuccessEvent
import com.example.payapi.pay.port.out.PaySuccessHandlerPort
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger { }

@Component
internal class PayEventAdapter(
    private val kafkaProducerTemplate: KafkaTemplate<String, String>,
) : PaymentFailHandlerPort, PaySuccessHandlerPort {
    companion object {
        private const val ORDER_EVENT_TOPIC = "order-event-topic"
    }

    override fun sendOrderPaymentRecover(orderPayFailEvent: OrderPayFailEvent) {
        logger.info("call recover handler order Id: {}, pay topic: {}", orderPayFailEvent.orderId, ORDER_EVENT_TOPIC)
        val orderFailCommand = OrderRawEvent.from(orderPayFailEvent)

        kafkaProducerTemplate.send(ORDER_EVENT_TOPIC, objectMapper.writeValueAsString(orderFailCommand))
    }

    override fun sendOrderPaySucessEvent(orderPaySuccessEvent: OrderPaySuccessEvent) {
        logger.info("call pay success handler order Id: {}, pay Id: {}, pay topic: {}", orderPaySuccessEvent.orderId, orderPaySuccessEvent.payId, ORDER_EVENT_TOPIC)
        val orderFailCommand = OrderRawEvent.from(orderPaySuccessEvent)

        kafkaProducerTemplate.send(ORDER_EVENT_TOPIC, objectMapper.writeValueAsString(orderFailCommand))
    }
}
