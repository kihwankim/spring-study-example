package com.example.kotlinexposeexample.entity

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

data class PaymentEntity(
    var id: Long = 0L,
    var orderId: Long,
    var amount: BigDecimal
)