package com.example.redisqueueexample.service

import com.example.redisqueueexample.domain.TestData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StreamLoggingService {

    fun doData(data: TestData) {
        log.info("[데이터 처리] call data. accountId: ${data.accountId}, name: ${data.name}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(StreamLoggingService::class.java)
    }
}