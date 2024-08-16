package org.example.coroutinetxexample.persistence.jpa.adapter

import org.example.coroutinetxexample.persistence.jpa.entity.TestDataJpaEntity
import org.example.coroutinetxexample.persistence.jpa.entity.TestDataJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TestDataJpaAdapter(
    private val testDataJpaRepository: TestDataJpaRepository,
) {

    @Transactional
    fun saveTwoDataIfInputIsNotTwo(input: Int) {
        testDataJpaRepository.save(
            TestDataJpaEntity(
                name = "name $input",
                input = input
            )
        )

        val nextInput = input + 1000

        testDataJpaRepository.save(
            TestDataJpaEntity(
                name = "name $nextInput",
                input = nextInput
            )
        )
    }
}