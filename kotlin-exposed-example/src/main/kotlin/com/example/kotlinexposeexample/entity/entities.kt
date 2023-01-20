package com.example.kotlinexposeexample.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

object PaymentTable : LongIdTable(name = "payment", columnName = "payment_id") {
    val orderId = long("order_id")
    val amount = decimal("amount", 19, 4)

    fun ResultRow.toEntity(): PaymentEntity {
        return PaymentEntity(
            id = get(id).value,
            orderId = get(orderId),
            amount = get(amount)
        )
    }
}

class PaymentDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PaymentDao>(PaymentTable)

    var amount by PaymentTable.amount
    var orderId by PaymentTable.orderId

    fun toEntity(): PaymentEntity = PaymentEntity(
        id = this.id.value,
        orderId = this.orderId,
        amount = this.amount
    )
}

data class PaymentEntity(
    var id: Long = 0L,
    var orderId: Long,
    var amount: BigDecimal
)