package com.example.orderapi.order.adapter

import com.example.common.domain.objectMapper
import com.example.common.event.DomainPayload
import com.example.orderapi.order.entity.OrderEventType
import com.example.orderapi.order.entity.OrderRawEvent
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class OrderEventConsumer(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @KafkaListener(id = "order-event-consumer-group", topics = ["order-event-topic"])
    fun orderEventListener(message: String) {
        logger.info("receive message from order: $message")

        try {
            val orderRawEvent = objectMapper.readValue(message, OrderRawEvent::class.java)
            val classVal = OrderEventType.findBySimepleType(orderRawEvent.getEventType())
            val rawEvent = objectMapper.readValue(orderRawEvent.getEventPayload(), classVal.classType.java) as DomainPayload<*>

            applicationEventPublisher.publishEvent(rawEvent)
        } catch (e: Exception) {
            logger.warn(e.message, e)
        }
    }
}