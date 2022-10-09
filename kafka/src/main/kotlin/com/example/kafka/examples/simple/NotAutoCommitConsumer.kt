package com.example.kafka.examples.simple

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetCommitCallback
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class NotAutoCommitConsumer {
    companion object {
        const val TOPIC_NAME = "test"
        const val BOOTSTRAP_SERVER = "localhost:9092"
        const val GROUP_ID = "test-group"
    }

    fun run() {
        val config = Properties()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        config[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        config[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

        val kafkaConsumer = KafkaConsumer<String, String>(config)
        kafkaConsumer.subscribe(listOf(TOPIC_NAME))

        while (true) {
            val records: ConsumerRecords<String, String> = kafkaConsumer.poll(Duration.ofSeconds(1))
            for (record in records) {
                print(record)
                commit(kafkaConsumer)
            }
        }
    }

    private fun commit(consumer: KafkaConsumer<String, String>, isSync: Boolean = true) {
        when (isSync) {
            true -> consumer.commitSync()
            false -> consumer.commitAsync(OffsetCommitCallback { offsets, exception ->
                if (exception != null) {
                    println("Commit failed, offset $offsets")
                } else {
                    print("Commit Success")
                }
            }
            )
        }
    }
}

fun main() {
    val notAutoCommitConsumer = NotAutoCommitConsumer()
    notAutoCommitConsumer.run()
}