package com.example.payapi.order.adapter

import com.example.common.event.DomainEvent
import com.example.payapi.common.objectMapper
import com.example.payapi.order.entity.OrderEventMessageType
import com.example.payapi.order.entity.OrderRawCommand
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class OrderEventConsumer(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @KafkaListener(id = "order-consumer-group", topics = ["order-event-topic"])
    fun orderEventListener(message: String) {
        logger.info("receive message from order: $message")

        try {
            val orderRawCommand = objectMapper.readValue(message, OrderRawCommand::class.java)
            val classVal = OrderEventMessageType.findBySimepleType(orderRawCommand.getType())
            val rawEvent = objectMapper.readValue(orderRawCommand.getPayload(), classVal.classType.java) as DomainEvent<*> // OrderPurchase

            applicationEventPublisher.publishEvent(rawEvent)
        } catch (e: Exception) {
            logger.warn(e.message, e)
        }
    }
}