package com.example.kafka.examples.simple

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class SimpleProducer {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SimpleProducer::class.java)

        const val TOPIC_NAME = "test"
        const val BOOT_STRAP_SERVERS = "localhost:9092"
    }

    fun run() {
        // producer 설정 값 지정
        val config = Properties()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOT_STRAP_SERVERS
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name

        val kafkaProducer = KafkaProducer<String, String>(config)

        // message 전송
        val message = "testMessage"
        val record = ProducerRecord<String, String>(TOPIC_NAME, message)
        kafkaProducer.send(record)
        logger.info("record: {}", record)

        // 모아서 보내는 경우 전제 전송을 위해서 flush 진행 -> 버퍼
        kafkaProducer.flush()
        kafkaProducer.close()
    }
}

fun main() {
    SimpleProducer().run()
}