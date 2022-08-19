package com.example.payapi.order.adapter

import com.example.payapi.pay.domain.command.PayCommand
import com.example.payapi.pay.port.out.PaymentFailHandlerPort
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger { }

@Component
internal class PayFailAdapter(
    private val kafkaProducerTemplate: KafkaTemplate<String, String>,
) : PaymentFailHandlerPort {
    companion object {
        private const val PAY_EVENT_TOPIC = "pay-event-topic"
    }

    override fun sendPaymentRecover(payCommand: PayCommand) {
        logger.info("call recover handler order Id: {}, pay topic: {}", payCommand.orderId, PAY_EVENT_TOPIC)
    }
}