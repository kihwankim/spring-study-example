package com.example.parallel_consumer.domain.common.log.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface DLogger {
    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)
}