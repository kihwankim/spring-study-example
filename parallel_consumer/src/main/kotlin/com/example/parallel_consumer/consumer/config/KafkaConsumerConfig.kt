package com.example.parallel_consumer.consumer.config

import com.example.parallel_consumer.consumer.test.TestConsumer
import io.confluent.parallelconsumer.ParallelConsumerOptions
import io.confluent.parallelconsumer.ParallelStreamProcessor
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import java.time.Duration

@Configuration
class KafkaConsumerConfig(
    private val kafkaProperties: KafkaProperties
) {

    companion object {
        private const val TEST_TOPIC = "test-topic"
    }

    @Bean
    fun testKeybasedParallelConsumer(
        testConsumer: TestConsumer
    ): ParallelStreamProcessor<String, String> {
        val options = ParallelConsumerOptions.builder<String, String>()
            .ordering(ParallelConsumerOptions.ProcessingOrder.KEY)
            .retryDelayProvider { Duration.ofMinutes(1) }
            .maxConcurrency(5)
            .commitMode(ParallelConsumerOptions.CommitMode.PERIODIC_CONSUMER_ASYNCHRONOUS)
            .consumer(KafkaConsumer(consumerConfigs()))
            .batchSize(10)
            .commitInterval(Duration.ofMillis(500))
            .build()
        val eosStreamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options).apply {
            subscribe(listOf(TEST_TOPIC))
            poll { context ->
                testConsumer.testConsumer(context)
            }
        }

        return eosStreamProcessor
    }

    @Bean
    fun testConsumer(): ConcurrentKafkaListenerContainerFactory<String, String> {
        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            setConsumerFactory(consumerFactory())
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
            containerProperties.isObservationEnabled = true
        }
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> = DefaultKafkaConsumerFactory(consumerConfigs())

    fun consumerConfigs(): Map<String, Any> = kafkaProperties.buildConsumerProperties() + mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to 10_000,
        ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to 3_000,
        ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 600_000,
        ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 50,
    )
}