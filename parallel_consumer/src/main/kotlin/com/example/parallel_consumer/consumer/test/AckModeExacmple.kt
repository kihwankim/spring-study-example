package com.example.parallel_consumer.consumer.test

import com.example.parallel_consumer.domain.common.log.domain.DLogger
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tools.jackson.databind.ObjectMapper
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

private const val ACK_MODE_TOPIC = "ack-mode-example-topic"
private val RETRY_DELAY = Duration.ofSeconds(1)
private val ERROR_WINDOW = Duration.ofSeconds(10)

data class AckModeExampleBody(val id: Int)

@Component
class AckModeExacmple(
    private val objectMapper: ObjectMapper,
) : DLogger {
    private val firstFailureTimes = ConcurrentHashMap<RecordId, Long>()

    @KafkaListener(
        topics = [ACK_MODE_TOPIC],
        containerFactory = "ackModeExampleListenerContainerFactory",
        errorHandler = "handler"
    )
    fun consume(record: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        val body = objectMapper.readValue(record.value(), AckModeExampleBody::class.java)
        process(record, body)
//        try {
//            val body = objectMapper.readValue(record.value(), AckModeExampleBody::class.java)
//            process(record, body)
//        } catch (exception: Exception) {
//            log.info("[ack mode example] processing failed: offset={}", record.offset(), exception)
//            acknowledgment.nack(RETRY_DELAY)
////            acknowledgment.nack(RETRY_DELAY)
////            return
//        }
//        acknowledgment.acknowledge()
    }

    private fun process(record: ConsumerRecord<String, String>, body: AckModeExampleBody) {
        val randomMillis = Random.nextLong(10L, 1_001L)
//        Thread.sleep(randomMillis)
        if (body.id != 10) {
            log.info("[ack mode example] success: id={}", body.id)
            return
        }

        val recordId = RecordId(record.topic(), record.partition(), record.offset())
        val now = System.nanoTime()
        val firstFailureTime = firstFailureTimes.putIfAbsent(recordId, now) ?: throw IllegalStateException("id 10 failed")

//        if (now - firstFailureTime < ERROR_WINDOW.toNanos()) {
//            firstFailureTimes.remove(recordId, firstFailureTime)
//            log.info("[ack mode example] error occurred within 10 seconds; success: id={}", body.id)
//            return
//        }

        firstFailureTimes[recordId] = now
        throw IllegalStateException("id 10 failed")
    }

    private data class RecordId(val topic: String, val partition: Int, val offset: Long)
}

@RestController
class AckModeExampleController(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    @GetMapping("/test-call")
    fun produce(@RequestParam size: Int): Map<String, Int> {
        val sends = (1..size).map { id ->
            kafkaTemplate.send(
                ACK_MODE_TOPIC,
                id.toString(),
                objectMapper.writeValueAsString(AckModeExampleBody(id)),
            )
        }
        CompletableFuture.allOf(*sends.toTypedArray()).join()
        return mapOf("produced" to size)
    }
}

@Configuration
class AckModeExacmpleConfig(
    private val kafkaProperties: KafkaProperties,
) {
    @Bean
    fun ackModeExampleTopic() = TopicBuilder.name(ACK_MODE_TOPIC).partitions(1).replicas(1).build()

    @Bean
    fun ackModeExampleListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val consumerConfigs = kafkaProperties.buildConsumerProperties() + mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to "${kafkaProperties.consumer.groupId ?: "ack-mode-example"}.ack-mode-example",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to 10_000,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to 3_000,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 600_000,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 50,
        )

        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            setConsumerFactory(DefaultKafkaConsumerFactory(consumerConfigs))
            containerProperties.ackMode = ContainerProperties.AckMode.RECORD
            containerProperties.pollTimeout = 1_000
            containerProperties.isObservationEnabled = true
        }
    }
}
