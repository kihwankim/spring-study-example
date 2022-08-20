package com.example.payapi.pay.domain.event

import com.example.common.event.DomainEvent
import java.math.BigDecimal

data class OrderPayFailEvent(
    val orderId: Long,
    val userId: Long,
    val totalPrice: BigDecimal,
) : DomainEvent<Long> {
    override fun getId(): Long = orderId
}