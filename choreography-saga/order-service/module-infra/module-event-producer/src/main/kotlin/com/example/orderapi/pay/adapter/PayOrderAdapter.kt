package com.example.orderapi.pay.adapter

import com.example.common.domain.objectMapper
import com.example.common.exception.PayServiceCallException
import com.example.orderapi.order.domain.model.OrderPurchase
import com.example.orderapi.order.domain.port.out.PayOrderPort
import com.example.orderapi.outbox.repository.OrderOutBoxQueryRepository
import com.example.orderapi.outbox.repository.OrderOutBoxRepository
import com.example.orderapi.pay.entity.OrderRawCommand
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

@Component
internal class PayOrderAdapter(
    private val kafkaProducerTemplate: KafkaTemplate<String, String>,
    private val orderOutBoxRepository: OrderOutBoxRepository,
    private val orderOutBoxQueryRepository: OrderOutBoxQueryRepository,
) : PayOrderPort {
    companion object {
        private const val ORDER_EVENT_TOPIC = "order-event-topic"
    }

    @Transactional
    override fun payProductsProcessor(orderPurchase: OrderPurchase) {
        val foundOrderOutBox = orderOutBoxQueryRepository.findByIdentityHashKey(orderPurchase.orderHashKey)
        val orderRawCommand = OrderRawCommand.from(orderPurchase)
        val commandMessage = objectMapper.writeValueAsString(orderRawCommand)
        logger.info("send payload: $commandMessage")

        kafkaProducerTemplate.send(ORDER_EVENT_TOPIC, commandMessage)
            .addCallback(
                { success -> orderOutBoxRepository.delete(foundOrderOutBox) },
                { err -> throw PayServiceCallException() }
            )
    }
}