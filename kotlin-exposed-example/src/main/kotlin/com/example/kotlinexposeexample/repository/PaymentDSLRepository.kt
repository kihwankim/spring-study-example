package com.example.kotlinexposeexample.repository

import com.example.kotlinexposeexample.entity.PaymentEntity
import com.example.kotlinexposeexample.entity.PaymentTable
import com.example.kotlinexposeexample.entity.PaymentTable.toEntity
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class PaymentDSLRepository {

    fun save(paymentEntity: PaymentEntity): PaymentEntity {
        val id = PaymentTable.insert {
            it[amount] = paymentEntity.amount
            it[orderId] = paymentEntity.orderId
        }[PaymentTable.id]
        paymentEntity.id = id.value

        return paymentEntity
    }

    fun findById(paymentId: Long): PaymentEntity? {
        return PaymentTable.select {
            PaymentTable.id.eq(paymentId)
        }.firstOrNull()?.toEntity()
    }

    fun saveBulk(listOfPaymentEntity: List<PaymentEntity>) {
        PaymentTable.batchInsert(
            listOfPaymentEntity,
            shouldReturnGeneratedValues = false
        ) {
            this[PaymentTable.amount] = it.amount
            this[PaymentTable.orderId] = it.orderId
        }
    }
}