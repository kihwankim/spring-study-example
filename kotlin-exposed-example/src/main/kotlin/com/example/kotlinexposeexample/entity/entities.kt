package com.example.kotlinexposeexample.entity

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object PaymentTable : Table(name = "payment") {
    val id = long("payment_id").autoIncrement()
    val orderId = long("order_id")
    val amount = decimal("amount", 19, 4)

    val pk = PrimaryKey(id, name = "pk_payment")
    fun ResultRow.toEntity(): PaymentEntity {
        return PaymentEntity(
            id = get(id),
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