package org.example.coroutinetxexample.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.example.coroutinetxexample.persistence.jpa.adapter.TestDataJpaAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TestDataService(
    private val testDataJpaAdapter: TestDataJpaAdapter,
) {
    companion object {
        private val log = LoggerFactory.getLogger(TestDataService::class.java)
    }

    fun saveLoop() {
        runBlocking(Dispatchers.IO) {
            (1..10).map { input ->
                async {
                    kotlin.runCatching {
                        testDataJpaAdapter.saveTwoDataIfInputIsNotTwo(input)
                    }.getOrNull()
                }
            }.awaitAll()
        }.let {
            log.info("data $it")
        }
    }
}