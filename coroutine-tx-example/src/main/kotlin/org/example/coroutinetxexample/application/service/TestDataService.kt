package org.example.coroutinetxexample.application.service

import org.example.coroutinetxexample.persistence.jpa.adapter.TestDataJpaAdapter
import org.springframework.stereotype.Service

@Service
class TestDataService(
    private val testDataJpaAdapter: TestDataJpaAdapter,
) {

    fun saveLoop() {

        (1..10).forEach { input ->
            kotlin.runCatching {
                testDataJpaAdapter.saveTwoDataIfInputIsNotTwo(input)
            }
        }
    }
}