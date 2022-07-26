package com.example.kafka.order.adapter.message

import com.example.kafka.order.adapter.message.dto.ProductMessageDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository


@Repository
class KafkaMessageSender(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : MessageSender {
    companion object {
        const val topic = "product-topic"
        val mapper: ObjectMapper = ObjectMapper()
    }

    override fun sendProductData(productMessage: ProductMessageDto) {
        kafkaTemplate.send(topic, mapper.writeValueAsString(productMessage))
    }
}