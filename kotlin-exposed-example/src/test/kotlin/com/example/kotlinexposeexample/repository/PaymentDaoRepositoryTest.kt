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
internal class PaymentDaoRepositoryTest {

    @Autowired
    lateinit var database: Database

    @Autowired
    lateinit var paymentDaoRepository: PaymentDaoRepository

    @Test
    fun save() {
        val paymentEntity = PaymentEntity(
            orderId = 1L,
            amount = BigDecimal(2.0)
        )
        transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDaoRepository.save(paymentEntity)
        }
    }

    @Test
    fun findById() {
        val paymentEntity = PaymentEntity(
            orderId = 1L,
            amount = BigDecimal(2.0)
        )
        val savedResult = transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDaoRepository.save(paymentEntity)
        }

        val result = transaction(database) {
            addLogger(StdOutSqlLogger)
            paymentDaoRepository.findById(paymentId = savedResult.id)
        }

        assertThat(result).isNotNull
        assertThat(result!!.id).isNotNull
        assertThat(result.orderId).isEqualTo(1L)
    }
}