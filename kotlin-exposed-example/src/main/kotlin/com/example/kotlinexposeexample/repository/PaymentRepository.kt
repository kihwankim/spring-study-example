package com.example.kotlinexposeexample.repository

import com.example.kotlinexposeexample.entity.PaymentEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class PaymentRepository(
    private val datasource: DataSource
) {

    fun findById(paymentId: Long) {
        Database.connect(datasource)
        transaction {
            addLogger(StdOutSqlLogger)
            PaymentEntity.select {
                PaymentEntity.orderId.eq(1)
            }.first()
        }
    }
}