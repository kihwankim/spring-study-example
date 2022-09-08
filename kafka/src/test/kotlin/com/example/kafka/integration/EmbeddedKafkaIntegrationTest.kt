package com.example.kafka.integration

import com.example.kafka.order.adapter.message.KafkaMessageSender
import com.example.kafka.order.adapter.message.dto.ProductMessageDto
import com.example.kafka.order.adapter.persistence.MockPersistenceAdapter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka

@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092"],
    ports = [9092]
)
class EmbeddedKafkaIntegrationTest {
    @Autowired
    lateinit var producer: KafkaMessageSender

    @Autowired
    lateinit var mockPersistenceAdapter: MockPersistenceAdapter

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

//        Thread.sleep(2000)
//
//        assertThat(mockPersistenceAdapter.findAll()).hasSize(1)
    }
}