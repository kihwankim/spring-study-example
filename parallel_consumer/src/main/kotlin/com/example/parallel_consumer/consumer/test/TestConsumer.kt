package com.example.parallel_consumer.consumer.test

import com.example.parallel_consumer.domain.common.log.domain.DLogger
import io.confluent.parallelconsumer.PollContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.stereotype.Component

@Component
class TestConsumer {
    companion object : DLogger

    fun testConsumer(context: PollContext<String, String>) {
        runCatching {
            context.streamConsumerRecords().toList().forEach { record ->
                doEachTestConsumer(record)
            }
        }.onFailure { e ->
            log.info("[test consumer error] error 발생", e)
        }
    }

    private fun doEachTestConsumer(record: ConsumerRecord<String, String>) {
        runCatching {
            val thradName = Thread.currentThread().name
            val key = record.key().toString()
            val payload = record.value()
            log.info("[처리 결과] threadName: $thradName, key: $key, value: $payload")
        }.onFailure { e ->
            log.info("[test consumer error] record proces error 발생", e)
        }
    }
}