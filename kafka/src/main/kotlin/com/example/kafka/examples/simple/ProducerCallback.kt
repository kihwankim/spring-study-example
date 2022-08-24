package com.example.kafka.examples.simple

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.RecordMetadata


class ProducerCallback : Callback {
    override fun onCompletion(metadata: RecordMetadata?, exception: Exception?) {
        if (exception != null) {
            println(exception.message)
        } else {
            println(metadata)
        }
    }
}