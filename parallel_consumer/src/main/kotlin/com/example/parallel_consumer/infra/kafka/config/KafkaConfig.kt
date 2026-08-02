package com.example.parallel_consumer.infra.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.kafka.autoconfigure.DefaultKafkaProducerFactoryCustomizer
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@EnableKafka
@Configuration
class KafkaConfig(
    private val kafkaProperties: KafkaProperties,
) {
    companion object

    @Bean
    fun kafkaTemplate(
        producerFactory: ProducerFactory<String, String>,
    ): KafkaTemplate<String, String> = KafkaTemplate(producerFactory).apply {
        setProducerInterceptor(ProducerTraceIdInterceptor())
    }

    @Bean
    fun producerFactory(
        customizers: ObjectProvider<DefaultKafkaProducerFactoryCustomizer>,
    ): ProducerFactory<String, String> = DefaultKafkaProducerFactory<String, String>(producerConfigs()).apply {
        customizers.orderedStream().forEach { it.customize(this) }
    }

    @Bean
    fun producerConfigs(): Map<String, Any> = kafkaProperties.buildProducerProperties() + mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
    )
}
