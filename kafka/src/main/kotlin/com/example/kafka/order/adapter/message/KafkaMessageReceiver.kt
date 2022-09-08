package com.example.kafka.order.adapter.message

import com.example.kafka.order.adapter.message.dto.ConsumerMessageDto
import com.example.kafka.order.domain.port.out.PersistncePort
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaMessageReceiver(
    private val persistecePort: PersistncePort
) : MessageReceiver {

    companion object {
        const val topic = "product-topic"
    }

    @KafkaListener(topics = [topic], id = "product-consumer-group")
    override fun receive(consumerMessageDto: ConsumerMessageDto) {
        persistecePort.save(consumerMessageDto.toDomainModel())
    }
}