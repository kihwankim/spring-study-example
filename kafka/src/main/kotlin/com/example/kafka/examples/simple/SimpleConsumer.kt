package com.example.kafka.examples.simple

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*


class SimpleConsumer {
    companion object {
        val TOPIC_NAME = "test"
        val BOOTSTRAP_SERVER = "localhost:9092"
        val GROUP_ID = "test-group"
    }

    fun run() {
        val config = Properties()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        config[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name

        val kafkaConsumer = KafkaConsumer<String, String>(config)
        kafkaConsumer.subscribe(listOf(TOPIC_NAME))

        while (true) {
            val records: ConsumerRecords<String, String> = kafkaConsumer.poll(Duration.ofSeconds(1))
            for (record in records) {
                print(record)
            }
        }
    }
}

fun main() {
    val simpleConsumer = SimpleConsumer()
    simpleConsumer.run()
}