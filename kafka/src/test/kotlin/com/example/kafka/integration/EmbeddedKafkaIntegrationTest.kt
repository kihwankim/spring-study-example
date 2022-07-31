package com.example.kafka.integration

import com.example.kafka.order.adapter.message.KafkaMessageReceiver
import com.example.kafka.order.adapter.message.KafkaMessageSender
import com.example.kafka.order.adapter.message.dto.ProductMessageDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import java.util.concurrent.TimeUnit

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class EmbeddedKafkaIntegrationTest {
    @Autowired
    lateinit var producer: KafkaMessageSender

    @Autowired
    lateinit var consumer: KafkaMessageReceiver

    @Test
    @Throws(Exception::class)
    fun `kafka test 코드 sender와 receiver - 성공`() {
        producer.sendProductData(
            ProductMessageDto(
                1L,
                "name",
                1
            )
        )

        consumer.latch.await(1000, TimeUnit.MILLISECONDS)

        assertThat(consumer.latch.count).isEqualTo(0L)
    }
}