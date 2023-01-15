package com.example.kotlinexposeexample.entity

import org.jetbrains.exposed.dao.id.LongIdTable

object PaymentEntity : LongIdTable(name = "payment") {
    val orderId = long("order_id")
    val amount = decimal("amount", 19, 4)
}