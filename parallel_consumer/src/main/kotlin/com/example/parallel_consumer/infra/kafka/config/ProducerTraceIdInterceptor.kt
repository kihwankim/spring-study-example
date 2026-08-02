package com.example.parallel_consumer.infra.kafka.config

import com.example.parallel_consumer.domain.common.log.constants.TRACE_REQUEST_HEADER
import com.example.parallel_consumer.domain.common.log.supporter.LoggingHelper
import org.apache.kafka.clients.producer.ProducerInterceptor
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.nio.charset.StandardCharsets

class ProducerTraceIdInterceptor : ProducerInterceptor<String, String> {
    override fun configure(configs: MutableMap<String, *>?) {}

    override fun close() {}

    override fun onAcknowledgement(metadata: RecordMetadata?, exception: Exception?) {}

    override fun onSend(record: ProducerRecord<String, String>): ProducerRecord<String, String> {
        LoggingHelper.createNextTraceHeader()?.run {
            record.headers().add(TRACE_REQUEST_HEADER, this.toByteArray(StandardCharsets.UTF_8))
        }

        return record
    }
}
