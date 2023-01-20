package com.example.kotlinexposeexample.repository

import com.example.kotlinexposeexample.entity.PaymentEntity
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class PaymentDSLRepositoryTest {

    @Autowired
    lateinit var database: Database

    @Autowired
    lateinit var paymentDSLRepository: PaymentDSLRepository

    @Test
    fun save() {
        // given
        val paymentEntity = PaymentEntity(
            orderId = 1L,
            amount = BigDecimal(2.0)
        )

        // when
        val savedEntity = transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDSLRepository.save(paymentEntity)
        }

        // then
        assertThat(savedEntity.id).isNotEqualTo(0L)
    }

    @Test
    fun findByIdTest() {
        // given
        val paymentEntity = PaymentEntity(
            orderId = 1L,
            amount = BigDecimal(2.0)
        )
        transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDSLRepository.save(paymentEntity)
        }

        // when
        val entity = transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDSLRepository.findById(1L)
        }

        // then
        assertThat(entity).isNotNull
        assertThat(entity!!.amount).isEqualTo(BigDecimal("2.0000"))
        assertThat(entity.orderId).isEqualTo(1L)
    }
}