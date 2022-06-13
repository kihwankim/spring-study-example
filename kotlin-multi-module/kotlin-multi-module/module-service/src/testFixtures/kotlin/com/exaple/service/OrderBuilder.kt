package com.exaple.service

import com.example.service.domain.Order

data class OrderBuilder(
    val id: String = "",
    val description: String = "",
    val amount: Long = 0L
) {
    fun build(): Order {
        return Order(
            id = id,
            description = description,
            amount = amount
        )
    }
}
