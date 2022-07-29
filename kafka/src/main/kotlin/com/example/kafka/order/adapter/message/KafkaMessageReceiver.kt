package com.example.kafka.order.adapter.message

import com.example.kafka.order.adapter.message.dto.ConsumerMessageDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class KafkaMessageReceiver(
    val latch: CountDownLatch = CountDownLatch(1)
) : MessageReceiver {

    companion object {
        const val topic = "product-topic"
    }

    @KafkaListener(topics = [topic])
    override fun receive(consumerMessageDto: ConsumerMessageDto) {
        print(consumerMessageDto)
    }
}