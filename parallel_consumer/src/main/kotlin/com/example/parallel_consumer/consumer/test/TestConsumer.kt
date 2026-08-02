package com.example.parallel_consumer.consumer.test

import io.confluent.parallelconsumer.PollContext
import org.springframework.stereotype.Component

@Component
class TestConsumer {
    fun testConsumer(context: PollContext<String, String>) {
        runCatching {

        }.onFailure {

        }
    }
}