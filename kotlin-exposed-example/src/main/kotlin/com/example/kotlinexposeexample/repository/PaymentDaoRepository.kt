package com.example.kotlinexposeexample.repository

import com.example.kotlinexposeexample.entity.PaymentDao
import com.example.kotlinexposeexample.entity.PaymentEntity
import com.example.kotlinexposeexample.entity.PaymentTable
import org.springframework.stereotype.Repository

@Repository
class PaymentDaoRepository {

    fun save(paymentEntity: PaymentEntity): PaymentEntity {
        val new: PaymentDao = PaymentDao.new {
            amount = paymentEntity.amount
            orderId = paymentEntity.orderId
        }
        paymentEntity.id = new.id.value

        return paymentEntity
    }

    fun findById(paymentId: Long): PaymentEntity? = PaymentDao.find { PaymentTable.id eq paymentId }.firstOrNull()?.toEntity()

    /**
     * 만약 sulk save를 희망할 경우 dao 방식은 불가능함
     */
//    fun saveBulk(listOfPaymentEntity: List<PaymentEntity>) {
//        // impossible
//    }
}