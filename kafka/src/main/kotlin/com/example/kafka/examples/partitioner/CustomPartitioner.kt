package com.example.kafka.examples.partitioner

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.InvalidRecordException
import org.apache.kafka.common.utils.Utils

class CustomPartitioner : Partitioner {
    override fun configure(configs: MutableMap<String, *>?) {
    }

    override fun close() {
    }

    override fun partition(topic: String?, key: Any?, keyBytes: ByteArray?, value: Any?, valueBytes: ByteArray?, cluster: Cluster): Int {
        if (keyBytes == null) {
            throw InvalidRecordException("Need message key")
        }
        if (key is String && key == "Pangyo") {
            return 0
        }

        val partitions = cluster.partitionsForTopic(topic)

        val numParitions = partitions.size

        return Utils.toPositive(Utils.murmur2(keyBytes)) % numParitions
    }
}