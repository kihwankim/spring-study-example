package org.example.coroutinetxexample.persistence.jpa.adapter

import org.example.coroutinetxexample.persistence.jpa.entity.TestDataJpaEntity
import org.example.coroutinetxexample.persistence.jpa.entity.TestDataJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TestDataJpaAdapter(
    private val testDataJpaRepository: TestDataJpaRepository,
) {

    companion object {
        private val log = LoggerFactory.getLogger(TestDataJpaAdapter::class.java)
    }

    @Transactional
    fun saveTwoDataIfInputIsNotTwo(input: Int): Int {
        log.info("Thread ${Thread.currentThread()}")
        testDataJpaRepository.save(
            TestDataJpaEntity(
                name = "name $input",
                input = input
            )
        )

        if (input == 2) {
            throw RuntimeException("error $input")
        }

        val nextInput = input + 1000

        testDataJpaRepository.save(
            TestDataJpaEntity(
                name = "name $nextInput",
                input = nextInput
            )
        )

        return input
    }
}