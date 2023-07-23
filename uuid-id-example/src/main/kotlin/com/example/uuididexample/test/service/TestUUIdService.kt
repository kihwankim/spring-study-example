package com.example.uuididexample.test.service

import com.example.uuididexample.test.entity.TestUUIdEntity
import com.example.uuididexample.test.repository.TestUUIdRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class TestUUIdService(
    private val testUUIdRepository: TestUUIdRepository,
) {

    @Transactional
    fun saveData(): TestUUIdEntity {
        val uuid = UUID.randomUUID().toString()

        return testUUIdRepository.save(
            TestUUIdEntity(
                name = uuid
            )
        )
    }
}