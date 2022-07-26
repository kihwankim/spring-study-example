package com.example.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import java.io.Serializable


@Configuration
@EnableKafka
class MessageConfig {
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(
        DefaultKafkaProducerFactory(producerConfigs())
    )

    @Bean
    fun producerConfigs(): Map<String, Serializable> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
    )
}