package com.example.orderapi.pay.adapter

import com.example.common.exception.PayServiceCallException
import com.example.orderapi.order.domain.dto.OrderPurchase
import com.example.orderapi.order.domain.port.out.PayOrderPort
import com.example.orderapi.outbox.repository.OrderOutBoxQueryRepository
import com.example.orderapi.outbox.repository.OrderOutBoxRepository
import com.example.orderapi.pay.entity.PayMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
internal class PayOrderAdapter(
    private val kafkaProducerTemplate: KafkaTemplate<String, String>,
    private val orderOutBoxRepository: OrderOutBoxRepository,
    private val orderOutBoxQueryRepository: OrderOutBoxQueryRepository,
) : PayOrderPort {
    companion object {
        private const val ORDER_EVENT_TOPIC = "order-event-topic"
        private val mapper: ObjectMapper = ObjectMapper()
    }

    @Transactional
    override fun payProductsProcessor(orderPurchase: OrderPurchase) {
        val foundOrderOutBox = orderOutBoxQueryRepository.findByIdentityHashKey(orderPurchase.orderHashKey)
        val payMessage = PayMessage.from(orderPurchase)
        kafkaProducerTemplate.send(ORDER_EVENT_TOPIC, mapper.writeValueAsString(payMessage))
            .addCallback(
                { success -> orderOutBoxRepository.delete(foundOrderOutBox) },
                { err -> throw PayServiceCallException() }
            )
    }
}